package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import entity.ResultBean;
import entity.User;
import service.UserService;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;

/**
 * 	用户控制器
 * @author junki
 * @date 2020年6月5日
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		
		switch (method) {
		case "login":
			login(request, response);
			break;
		case "regist":
			regist(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		case "check":
			check(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/error/404.jsp");
			break;
		}
		
	}

	/**
	 * 检查用户名是否重复
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String username = request.getParameter("username");
		
		// 调用service方法
		boolean result = false;
		try {
			result = userService.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 封装ajax请求结果对象
		ResultBean resutBean = ResultBean.builder().code(200).message("success").data(result).build();
		
		// 将ajax结果对象转为json字符串
		String jsonString = JSON.toJSONString(resutBean);
		
		// 将json字符串写回给前端
		//System.out.println(jsonString);
		// {"code":200,"data":false,"message":"success"}
		response.getWriter().print(jsonString);
		
	}

	/**
	 * 	注册方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 获取请求参数并封装
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		// 调用service方法保存用户
		int result = 0;
		try {
			result = userService.add(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 判断是否注册成功
		if (result == 0) {
			request.setAttribute("message", "注册失败，请重试！");
			request.setAttribute("user", user);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/user/login.jsp");
		}
		
	}

	/**
	 * 	退出登录方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("onlineUser");
		response.sendRedirect(request.getContextPath() + "/user/login.jsp");
	}

	/**
	 * 	登录方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ResultBean resultBean = null;
		
		// 获取用户输入验证码
		String userCaptcha = request.getParameter("captcha");
		// 获取正确的验证码
		String captcha = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isBlank(userCaptcha)) {
			resultBean = ResultBean.builder().code(400).message("验证码不能为空！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		} 
		if (!userCaptcha.equalsIgnoreCase(captcha)) {
			resultBean = ResultBean.builder().code(400).message("验证码错误！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		}
		
		// 判断用户名是否为空
		String username = request.getParameter("username");
		if (StringUtils.isBlank(username)) {
			resultBean = ResultBean.builder().code(400).message("用户名不能为空！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		}
		
		// 判断密码是否为空
		String password = request.getParameter("password");
		if (StringUtils.isBlank(password)) {
			resultBean = ResultBean.builder().code(400).message("密码不能为空！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		}
		
		// 调用登录业务方法
		User result = null;
		try {
			result = userService.login(User.builder().username(username).password(password).build());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 判断是否登录成功
		if (result == null) {
			resultBean = ResultBean.builder().code(400).message("用户名或密码错误！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		} else {
//			// 保存session
			request.getSession().setAttribute("onlineUser", result);
//			
			// 判断cookie
			String rememberMe = request.getParameter("rememberMe");
			if ("rememberMe".equals(rememberMe)) {
				Cookie usernameCookie = new Cookie("username", username);
				Cookie passwordCookie = new Cookie("password", password);
				Cookie rememberMeCookie = new Cookie("rememberMe", rememberMe);
				usernameCookie.setMaxAge(60 * 60 * 24 * 7);
				passwordCookie.setMaxAge(60 * 60 * 24 * 7);
				rememberMeCookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
				response.addCookie(rememberMeCookie);
			} else {
				Cookie usernameCookie = new Cookie("username", null);
				Cookie passwordCookie = new Cookie("password", null);
				Cookie rememberCookie = new Cookie("rememberMe", null);
				usernameCookie.setMaxAge(0);
				passwordCookie.setMaxAge(0);
				rememberCookie.setMaxAge(0);
				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
				response.addCookie(rememberCookie);
			}
			
			resultBean = ResultBean.builder().code(200).message("登陆成功！").build();
			response.getWriter().print(JSON.toJSONString(resultBean));
			return;
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
