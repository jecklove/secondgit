package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import service.PostService;


/**
 * 	帖子控制器
 * @author junki
 * @date 2020年6月5日
 */
@WebServlet("/post")
public class PostController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private PostService postService = new PostService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		
		switch (method) {
		case "list":
			list(request, response);
			break;
		case "add":
			add(request, response);
			break;
		case "detail":
			detail(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/error/404.jsp");
			break;
		}
		
	}

	/**
	 * 	查询详情
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void detail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String idStr = request.getParameter("id");
		// 判断是否可以转为数字
		if (!StringUtils.isNumeric(idStr)) {
			response.sendRedirect(request.getContextPath() + "/error/404.jsp");
			return;
		}
		
		Long id = Long.valueOf(idStr);
		
		// 调用service，通过id获取一篇帖子
		Post post = null;
		try {
			post = postService.getById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 转发到页面
		request.setAttribute("post", post);
		request.getRequestDispatcher("/post/detail.jsp").forward(request, response);
		
	}

	/**
	 * 	发帖方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// 封装帖子数据
		Post post = new Post();
		try {
			BeanUtils.populate(post, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		// 设置用户id
		User onlineUser = (User)request.getSession().getAttribute("onlineUser");
		post.setUser_id(onlineUser.getId());
		
		// 设置发帖时间
		post.setCreate_time(new Date());
		
		// 设置摘要
		if (post.getContent().length() <= 8) {
			post.setSummary(post.getContent());
		} else {
			post.setSummary(post.getContent().substring(0, 8) + "...");
		}
		
		// 调用service方法
		int result = 0;
		try {
			result = postService.add(post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 判断是否发帖成功
		if (result == 0) {
			request.setAttribute("message", "发帖失败，请稍后重试！");
			request.setAttribute("post", post);
			request.getRequestDispatcher("/post/add.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/post?method=list");
		}
		
	}

	/**
	 * 	获取帖子列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 获取分页参数
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		
		Long pageNum = 1L;
		Long pageSize = 5L;
		
		if (StringUtils.isNumeric(pageNumStr)) {
			pageNum = Long.valueOf(pageNumStr);
		}
		
		if (StringUtils.isNumeric(pageSizeStr)) {
			pageSize = Long.valueOf(pageSizeStr);
		}
		
		// 封装查询参数
		Post post = new Post();
		try {
			BeanUtils.populate(post, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		// 获取数据
		Pager<Post> pager = null;
		try {
			pager = postService.getList(post, pageNum, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 转发页面
		request.setAttribute("post", post);
		request.setAttribute("pager", pager);
		request.getRequestDispatcher("/post/list.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
