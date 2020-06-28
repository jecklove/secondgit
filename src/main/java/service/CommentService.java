package service;

import java.sql.SQLException;

import dao.CommentDao;
import entity.Comment;

public class CommentService {
	
	private CommentDao commentDao = new CommentDao();

	/**
	 * 
	 * @param comment
	 * @return
	 * @throws SQLException 
	 */
	public int add(Comment comment) throws SQLException {
		return commentDao.insert(comment);
	}

}
