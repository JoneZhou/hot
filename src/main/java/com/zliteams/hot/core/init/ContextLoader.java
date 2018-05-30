package com.zliteams.hot.core.init;

import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.core.env.Environment;

import com.zliteams.hot.core.BaseContext;
import com.zliteams.hot.core.HttpSchema;
import com.zliteams.hot.core.RunMode;
import com.zliteams.hot.core.SpringContext;


public class ContextLoader {

	private static final Logger logger = LoggerFactory.getLogger(ContextLoader.class);

	public static final String CLASS_PATH = "/WEB-INF/classes/";
	public static final String SCHEMA = "server.schema";
	public static final String HOST = "server.host";

	/**
	 * 初始化BaseContext
	 * 
	 * @param context
	 */
	public static void initContext(ServletContext context) {
		String rootPath = getRootPath(context);
		BaseContext.setRootPath(rootPath);
		BaseContext.setClassPath(rootPath + CLASS_PATH);

		String contextPath = context.getContextPath();
		BaseContext.setContextPath(contextPath);

		Properties applicationProperties = SpringContext.getBean("applicationProperties");
		BaseContext.setRunMode(buildRunMode());

		HttpSchema httpSchema = HttpSchema.valueOf(applicationProperties.getProperty(SCHEMA));
		BaseContext.setSchema(httpSchema);

		String host = applicationProperties.getProperty(HOST);
		BaseContext.setHost(host);

		BaseContext.setStartTime(new Date());
	}

	/**
	 * 获取根目录
	 */
	private static String getRootPath(ServletContext context) {
		String rootPath = context.getRealPath("");
		if (rootPath == null) {// resources are in a .war (JBoss, WebLogic)
			try {
				URL url = context.getResource("");
				rootPath = url.getPath();
			} catch (Exception e) {
				logger.error("Getting root path failed!", e);
			}
		}
		logger.info("RootPath:{}", rootPath);
		return rootPath;
	}

	private static RunMode buildRunMode() {
		Environment springEnvironment = SpringContext.getApplicationContext().getEnvironment();
		String[] profiles = springEnvironment.getActiveProfiles();
		if (profiles == null || profiles.length == 0) {
			profiles = springEnvironment.getDefaultProfiles();
		}
		RunMode runMode = RunMode.develop;
		try {
			runMode = RunMode.valueOf(profiles[0]);
			logger.info("RunMode:{}", runMode.toString());
		} catch (Exception e) {
			logger.info("default profiles: develop");
		}
		return runMode;
	}

}
