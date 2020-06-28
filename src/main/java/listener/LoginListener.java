package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import entity.User;

/**
 * 	监听session，防止同一账号多端登录
 * @author junki
 * @date 2020年6月10日
 */
@WebListener
public class LoginListener implements HttpSessionAttributeListener {

	// 创建map集合，用于保存用户名和对应的session
	private Map<String, HttpSession> map = new HashMap<>();
	
	public void attributeAdded(HttpSessionBindingEvent event)  { 

		String name = event.getName();
		// 判断保存的数据是否是登录用户信息
		if ("onlineUser".equals(name)) {
			User user = (User)event.getValue();
			
			// 判断该用户名是否已经存在（是否已经登录）
			if (map.containsKey(user.getUsername())) {
				// 让之前的登录session失效
				map.get(user.getUsername()).invalidate();
			}
			
			// 将新的session保存到map
			map.put(user.getUsername(), event.getSession());
		}
		
	}

    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	String name = event.getName();
		if ("onlineUser".equals(name)) {
			User user = (User)event.getValue();
			map.remove(user.getUsername());
		}
    }

    public void attributeReplaced(HttpSessionBindingEvent event)  { 

    }
	
}
