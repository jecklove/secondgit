package service;

import java.sql.SQLException;

import dao.UserDao;
import entity.User;

/**
 * 	用户业务类
 * @author junki
 * @date 2020年6月5日
 */
public class UserService {
	
	private UserDao userDao = new UserDao();

	/**
	 * 	用户登录业务方法
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public User login(User user) throws SQLException {
		return userDao.findOneByUsernameAndPassword(user);
	}

	/**
	 * 	添加用户
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public int add(User user) throws SQLException {
		return userDao.insert(user);
	}

	/**
	 * 检查用户名
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkUsername(String username) throws SQLException {
		User result = userDao.findOneByUsername(username);
		return result == null;
	}

}
