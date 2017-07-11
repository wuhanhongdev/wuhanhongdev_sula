<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>assets/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
	
	<script type="text/javascript" src="<%=basePath%>assets/js/angular.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/angular-ui-router.min.js"></script>
	<script type="text/javascript" src="assets/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="assets/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" src="assets/ueditor/angular-ueditor.js"></script>
  </head>
  
  <body>
  	<div ng-app="myApp" ng-controller="myCtrl">
  		<div class="ueditor" ng-model="content"></div>
  		{{content}}
  	</div>
    
    <script>
		var app = angular.module('myApp', ['ng.ueditor']);
		app.controller('myCtrl', function($scope, $http,$window ) {
			
			
		});
	</script>
  </body>
</html>
