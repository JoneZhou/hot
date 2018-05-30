package com.zliteams.hot.core;

import java.util.Date;

public class BaseContext {
	private static String rootPath;
	private static String classPath;
	private static String contextPath;
	private static String host;
	private static Date startTime;
	private static RunMode runMode;
	private static HttpSchema schema;

	/**
	 * web根目录，不包含结尾的"/"
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return rootPath;
	}

	public static void setRootPath(String rootPath) {
		BaseContext.rootPath = rootPath;
	}

	public static String getClassPath() {
		return classPath;
	}

	public static void setClassPath(String classPath) {
		BaseContext.classPath = classPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		BaseContext.contextPath = contextPath;
	}

	public static Date getStartTime() {
		return startTime;
	}

	public static void setStartTime(Date startTime) {
		BaseContext.startTime = startTime;
	}

	public static RunMode getRunMode() {
		return runMode;
	}

	public static void setRunMode(RunMode runMode) {
		BaseContext.runMode = runMode;
	}

	public static HttpSchema getSchema() {
		return schema;
	}

	public static void setSchema(HttpSchema schema) {
		BaseContext.schema = schema;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		BaseContext.host = host;
	}

}
