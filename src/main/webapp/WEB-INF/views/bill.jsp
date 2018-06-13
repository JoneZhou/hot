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
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap/css/bootstrap.css"/>
    </head>
    <body>
    	<form id="create_bill" action="rest/user/createBill" method="post">
    		<p></p>
    		类型：
    		<select id="j_bill-type" name="type">
    			<option value="0">支出</option>
    			<option value="1">收入</option>
    		</select>
    		
    		<p></p>
    		金额：
    		<input name="money" type="number"></input>
    		
    		<p></p>
    		类别：
    		<select name="category">
    			<c:forEach items="${categorys }" var="category">
	    			<option value="${category.id }">${category.name }</option>
    			</c:forEach>
    		</select>
    		
    		<p></p>
    		描述：
    		<textarea rows="3" cols="21" name="describe"></textarea>
    		
    		
    		<p></p>
    		<button type="button" onclick="submitBill()">账单提交</button>
    	</form>
    	<p>----------------------------------------------------------------------------</p>
    	<p>----------------------------------------------------------------------------</p>
    	<form id="create_category" action="rest/user/createCategory" method="post">
    		<p></p>
    		类型：
    		<select name="type">
    			<option value="0">支出</option>
    			<option value="1">收入</option>
    		</select>
    		
    		<p></p>
    		名称：
    		<input name="name" type="text"></input>
    		
    		<p></p>
    		<button type="button" onclick="submitCategory()">创建类别</button>
    	</form>
    	<p>----------------------------------------------------------------------------</p>
    	<p>----------------------------------------------------------------------------</p>
    	<button type="button" onclick="getBillPage()">获取列表</button>
    	<ul class="j_bill-page">
    	</ul>

        <script src="assets/plugins/jquery/jquery-1.11.1.js" type="text/javascript" charset="utf-8"></script>
        <script src="assets/plugins/bootstrap/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8">${categorysStr}</script>
        <script src="assets/scripts/bill.js" type="text/javascript" charset="utf-8"></script>
		
    </body>
</html>