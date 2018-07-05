<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <base href="<%=basePath%>">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <title></title>
        <link rel="shortcut icon" href="app/img/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap/css/bootstrap.css"/>
        <!-- 引入libs -->
    	<script src="/app/lib/sugar/sugar-1.4.0.min.js"></script>
    	<script src="/app/lib/jquery/jquery.min.js"></script>
    	<script src="/app/lib/underscore/underscore.js"></script>
    	<script src="/app/lib/seajs/sea-debug.js"></script>
    	<script src="/app/lib/seajs/seajs-text-debug.js"></script>
    	<script src="/app/lib/backbone/backbone.js"></script>
    	
    	
        <!-- 引入 ECharts 文件 -->
    	<script src="/app/plugins/echarts/echarts.min.js"></script>
    	
    	
    	<style type="text/css">
    		.table td {
    			vertical-align: middle !important;
    		}
    	</style>
    </head>
    <body>
    	<p></p>
	    <div class="container">
			<div class="searchContainer"></div>
			<div class="mainContainer"></div>
	    </div>

		<script src="/assets/plugins/bootstrap/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
			<script type="text/javascript">
			// seajs 的简单配置
			seajs.config({
			  base: "/app/js/",
			  paths: {
				  "tpl": "templates"
			  }
			});
	
			// 加载入口模块
			seajs.use("core/main.js");
		</script>
    </body>
</html>