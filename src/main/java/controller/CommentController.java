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

import org.apache.commons.beanutils.BeanUtils;
import entity.Comment;
import entity.User;
import service.CommentService;

/**
 * 	评论控制器
 * @author junki
 * @date 2020年6月5日
 */
@WebServlet("/comment")
public class CommentController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private CommentService commentService = new CommentService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		
		switch (method) {
		case "add":
			add(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/error/404.jsp");
			break;
		}
		
	}

	/**
	 * 	发表评论方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Comment comment = new Comment();
		
		try {
			BeanUtils.populate(comment, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		// 设置userId
		User onlineUser = (User)request.getSession().getAttribute("onlineUser");
		comment.setUser_id(onlineUser.getId());
		
		// 设置创建时间
		comment.setCreate_time(new Date());
		
		// 调用service方法
		int result = 0;
		try {
			result = commentService.add(comment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 判断是否评论成功
		if (result == 0) {
			request.setAttribute("message", "评论失败，请重试！");
			request.setAttribute("content", comment.getContent());
			request.getRequestDispatcher("/post?method=detail&id=" + comment.getPost_id()).forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/post?method=detail&id=" + comment.getPost_id());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
