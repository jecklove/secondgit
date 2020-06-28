package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;

/**
 * 	登录判断过滤器
 * @author junki
 * @date 2020年6月9日
 */
public class LoginFilter extends BaseFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 强制类型转换
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		// 获取上下文路径
		String bp = httpRequest.getContextPath();
		
		// 判断本次请求是否需要过滤
		if (checkExclude(httpRequest)) {
			chain.doFilter(request, response);
			return;
		}
		
		// 判断用户是否登录
		User onlineUser = (User)httpRequest.getSession().getAttribute("onlineUser");
		if (onlineUser == null) {
			// 未登录，跳转到403页面（也可以跳转到登录页或者其他自定义页面）
			httpResponse.sendRedirect(bp + "/error/403.jsp");
			return;
		}
		
		// 如果已登录，直接放行
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	
}
