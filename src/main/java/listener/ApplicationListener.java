package listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author junki
 * @date 2020年6月12日
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	// 将bp设置在application域中
    	sce.getServletContext().setAttribute("bp", sce.getServletContext().getContextPath());
    	
    	// 注册String --> Date转换器
		ConvertUtils.register(new Converter() {
			
			// type指的是要转换成的类型，Date
			// value指的是待转换的数值
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Date convert(Class type, Object value) {
				SimpleDateFormat sdf = null;
				if (StringUtils.isNoneBlank(value.toString())) {
					if (value.toString().contains(":")) {
						sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					} else {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					
					try {
						return sdf.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		}, Date.class);
    }
	
}
