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
<head>
<meta charset="utf-8">
<script src="http://www.w3school.com.cn/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
         window.onload = function WebSocketTest()
         {
            if ("WebSocket" in window)
            {
               
               // 打开一个 web socket
               var ws = new WebSocket("ws://localhost:5000/ws");
                window.ws = ws;
                              
               ws.onmessage = function (evt) 
               { 
                  var received_msg = evt.data;
				   $('#content').val('');
				   $('#chat').append(received_msg+ '<br><br>' );
               };
                
               ws.onclose = function()
               { 
                  // 关闭 websocket
                  alert("连接已关闭..."); 
               };
            }
            
            else
            {
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
		 }
      </script>

</head>
</head>
<body>
	<div id="sse">
		<br> <input id="content" type="text"></input> <a
			href="javascript:send((new Date()).toLocaleString())">发送消息</a> <a
			href="javascript:clear()">清空消息</a>
	</div>

	<div id="chat"></div>
</body>
</html>