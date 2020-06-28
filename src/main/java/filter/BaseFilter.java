package filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 	过滤器基类
 * 	实现配置式排除无需过滤的请求
 * @author junki
 * @date 2020年6月9日
 */
public class BaseFilter {

	private String[] excludePage = null;
	private String[] excludeMethod = null;
	private String[] excludePath = null;
	
	public void init(FilterConfig fConfig) throws ServletException {
		
		// 获取排除的页面
		String excludePageStr = fConfig.getInitParameter("excludePage");
		if (StringUtils.isNoneBlank(excludePageStr)) {
			if (excludePageStr.contains(",")) {
				excludePage = excludePageStr.split(",");
			} else {
				excludePage = new String[] {excludePageStr};
			}
		}
		
		// 获取排除的接口方法
		String excludeMethodStr = fConfig.getInitParameter("excludeMethod");
		if (StringUtils.isNoneBlank(excludeMethodStr)) {
			if (excludeMethodStr.contains(",")) {
				excludeMethod = excludeMethodStr.split(",");
			} else {
				excludeMethod = new String[] {excludeMethodStr};
			}
		}
		
		// 获取排除的路径
		String excludePathStr = fConfig.getInitParameter("excludePath");
		if (StringUtils.isNoneBlank(excludeMethodStr)) {
			if (excludePathStr.contains(",")) {
				excludePath = excludePathStr.split(",");
			} else {
				excludePath = new String[] {excludePathStr};
			}
		}
	}
	
	/**
	 * 	验证请求是否需要过滤
	 * @param httpRequest
	 * @return
	 */
	public boolean checkExclude(HttpServletRequest httpRequest) {
		
		// 获取请求地址
		String requestURL = httpRequest.getRequestURL().toString();
		
		// 获取上下文路径
		String bp = httpRequest.getContextPath();
		
		// 放行排除的页面
		for (String page : excludePage) {
			if (requestURL.endsWith(bp + page)) {
				return true;
			}
		}
		
		// 放行排除的接口方法
		for (String methodPath : excludeMethod) {
			String path = methodPath.split("\\?")[0];
			String method = methodPath.split("\\?")[1].split("=")[1];
			if (requestURL.endsWith(bp + path) && method.equals(httpRequest.getParameter("method"))) {
				return true;
			}
		}
		
		// 放行排除的路径
		for (String path : excludePath) {
			// 将表达式转为正则
			String pattern = path.replace(".", "\\.")
									.replace("?", "\\?")
									.replace("+", "\\+")
									.replace("*", ".+");
			if (requestURL.matches(".+" + bp + pattern)) {
				return true;
			}
		}
		
		return false;
	}
	
}
