package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import entity.Post;
import utils.DataSourceUtil;

/**
 *	帖子数据操作类
 * @author junki
 * @date 2020年6月5日
 */
public class PostDao {

	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public List<Post> findList(Post post, Long startIndex, Long pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		StringBuilder sbSql = new StringBuilder("SELECT post.*,`user`.nickname,COUNT(post_id) AS comment_count FROM post INNER JOIN `user` ON post.user_id = `user`.id LEFT JOIN `comment` ON post.id = `comment`.post_id WHERE 1=1");
		
		// 创建集合用于保存查询参数
		List<Object> params = new ArrayList<>();
		
		// 动态sql
		if (StringUtils.isNotBlank(post.getTitle())) {
			sbSql.append(" AND post.title LIKE ?");
			params.add("%" + post.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(post.getContent())) {
			sbSql.append(" AND post.content LIKE ?");
			params.add("%" + post.getContent() + "%");
		}
		if (post.getType() != null && post.getType() != -1) {
			sbSql.append(" AND post.type = ?");
			params.add(post.getType());
		}
		if (post.getCreate_time_begin() != null && post.getCreate_time_end() != null) {
			sbSql.append(" AND post.create_time BETWEEN ? AND ?");
			params.add(post.getCreate_time_begin());
			params.add(post.getCreate_time_end());
		}
		
		// 拼接分页
		sbSql.append(" GROUP BY post.id ORDER BY post.create_time DESC LIMIT ?,?");
		params.add(startIndex);
		params.add(pageSize);
		
		List<Post> result = qr.query(sbSql.toString(), new BeanListHandler<>(Post.class), params.toArray());
		return result;
	}

	/**
	 * 
	 * @param post
	 * @return
	 * @throws SQLException 
	 */
	public int insert(Post post) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "insert into post(title,type,summary,content,create_time,user_id) value(?,?,?,?,?,?)";
		int result = qr.update(sql, post.getTitle(), post.getType(), post.getSummary(), post.getContent(), post.getCreate_time(), post.getUser_id());
		return result;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public Post findOneById(Long id) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "select post.*,user.nickname from post left join user on post.user_id = user.id where post.id = ?";
		Post result = qr.query(sql, new BeanHandler<>(Post.class), id);
		return result;
	}

	/**
	 * 获取总数据量
	 * @return
	 * @throws SQLException 
	 */
	public Long count(Post post) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		StringBuilder sbSql = new StringBuilder("select count(*) from post WHERE 1=1");
		
		// 创建集合用于保存查询参数
		List<Object> params = new ArrayList<>();
		
		// 动态sql
		if (StringUtils.isNotBlank(post.getTitle())) {
			sbSql.append(" AND post.title LIKE ?");
			params.add("%" + post.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(post.getContent())) {
			sbSql.append(" AND post.content LIKE ?");
			params.add("%" + post.getContent() + "%");
		}
		if (post.getType() != null && post.getType() != -1) {
			sbSql.append(" AND post.type = ?");
			params.add(post.getType());
		}
		if (post.getCreate_time_begin() != null && post.getCreate_time_end() != null) {
			sbSql.append(" AND post.create_time BETWEEN ? AND ?");
			params.add(post.getCreate_time_begin());
			params.add(post.getCreate_time_end());
		}
		
		Long result = (Long)qr.query(sbSql.toString(), new ScalarHandler<>(1), params.toArray());
		return result;
	}

}
