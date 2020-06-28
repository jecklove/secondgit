package service;

import java.sql.SQLException;
import java.util.List;

import dao.*;
import entity.*;


public class PostService {
	
	private PostDao postDao = new PostDao();
	
	private CommentDao commentDao = new CommentDao();

	/**
	 * @return
	 * @throws SQLException 
	 */
	public Pager<Post> getList(Post post, Long pageNum, Long pageSize) throws SQLException {
		// 查询总数据量
		Long total = postDao.count(post);
		
		// 构建pager对象
		Pager<Post> pager = new Pager<>(pageNum, pageSize, total);
		
		// 分页查询数据
		List<Post> list = postDao.findList(post, pager.getStartIndex(), pager.getPageSize());
		pager.setData(list);
		
		return pager;
	}

	/**
	 * 
	 * @param post
	 * @return
	 * @throws SQLException 
	 */
	public int add(Post post) throws SQLException {
		return postDao.insert(post);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public Post getById(Long id) throws SQLException {
		Post post = postDao.findOneById(id);
		List<Comment> comments = commentDao.findByPostId(post.getId());
		post.setComments(comments);
		return post;
	}

}
