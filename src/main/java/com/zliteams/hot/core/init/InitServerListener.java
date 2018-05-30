package com.zliteams.hot.core.init;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zliteams.hot.core.SpringContext;


public class InitServerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		ContextLoader.initContext(context);
		
		//InitServerService service
		Map<String, InitServerService> services = SpringContext.getBeans(InitServerService.class);
		for(InitServerService service:services.values()){
			service.initServer();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//TODO
	}

}
