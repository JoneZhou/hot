<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title></title>
<link rel="shortcut icon" href="assets/app/img/favicon.ico"/>
<head>
<meta charset="utf-8">
<script type="text/javascript">
         
	/* window.onload = function WebSocketTest() {
		if ("WebSocket" in window) {

			// 打开一个 web socket
			var ws = new WebSocket("ws://localhost:5000/ws");
			window.ws = ws;

			ws.onmessage = function(evt) {
				var received_msg = evt.data;
				$('#content').val('');
				$('#chat').append(received_msg + '<br><br>');
			};

			ws.onclose = function() {
				// 关闭 websocket
				alert("连接已关闭...");
			};
		}

		else {
			// 浏览器不支持 WebSocket
			alert("您的浏览器不支持 WebSocket!");
		}
	}
	function send(msg) {
		var content = $('#content').val();
		window.ws.send(content);
	}
	function clear() {
		$('#chat').html('');
	} */
</script>

</head>
</head>
<body>
	<div id="sse">
		<br> <input id="content" type="text"></input>
		<a href="javascript:send((new Date()).toLocaleString())">发送消息</a> 
		<a href="javascript:clear()">清空消息</a>
		<br>
		<a class="bill">记录</a>
		<a class="getBills">查询</a>
	</div>

	<div id="chat"></div>
	
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
		 2018 &copy; Zliteams - Hot
	</div>
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
		<script src="assets/plugins/respond.min.js"></script>
		<script src="assets/plugins/excanvas.min.js"></script> 
		<![endif]-->
	<script src="assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="assets/scripts/app.js" type="text/javascript"></script>
	<script src="assets/scripts/ws.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Ws.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>