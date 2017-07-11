<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>sula</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png"
	href="<%=basePath%>assets/i/favicon.png">
<link rel="stylesheet" href="<%=basePath%>assets/css/amazeui.min.css" />
<script type="text/javascript" src="<%=basePath%>assets/js/angular.js"></script>
<script src="<%=basePath%>assets/js/jquery.min.js"></script>
<script src="<%=basePath%>assets/js/amazeui.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>index.js"></script>
<style type="text/css">
body {
	background: url(css/login_bg.jpg) center center;
	background-size: cover;
}

.sl-login {
	width: 500px;
	height: 360px;
	position: fixed;
	top: 50%;
	left: 50%;
	margin-left: -250px;
	margin-top: -280px;
	z-index: 100;
	_position: absolute; /*这里开始的2句代码是为ie6不兼容position:fixed;而写的*/
	_top: expression(eval(document.documentElement.scrollTop +160) );
	/*这里需要获取滚动高度+元素原本的高度*/
}

.sl-login .login-box {
	width: 500px;
	height: 300px;
	background: #fff;
	border-radius: 10px;
	display: block;
	box-shadow: 0 0 10px #0CC;
	padding: 50px 110px;
}

.sl-login h1 {
	width: 500px;
	color: #fff;
	text-align: center;
	font-size: 40px
}

.sl-login i {
	color: #60a7e5
}

.sl-login .am-alert {
	background-color: #a4d3fc;
	border: #66b0ea solid 1px;
}

.sl-login-coptright {
	width: 200px;
	margin: 0 auto;
	position: fixed;
	bottom: 30px;
	left: 50%;
	margin-left: -100px;
	text-align: center;
	color: #333
}
</style>
</head>
<body ng-app="myApp" ng-controller="myCtrl">

	<div class="sl-login">
		<h1>速拉货运后台管理系统</h1>
		<div class="login-box">
			<form method="post" class="am-form">
				<div class="am-form-group am-form-icon">
					<i class="am-icon-user"></i> <input class="am-form-field am-radius"
						type="text" name="account" id="account"
						ng-model="formData.account" value="">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-key"></i> <input class="am-form-field am-radius"
						type="password" name="password" id="password"
						ng-model="formData.password" value="">
				</div>
				<div class="am-cf">
					<input type="submit" name="" value="登 录"
						class="am-btn am-btn-primary am-btn-sm am-fl am-animation-slide-bottom"
						ng-click="login()"> <input type="submit" name=""
						value="忘记密码 ^_^? "
						class="am-btn am-btn-default am-btn-sm am-fr am-animation-slide-bottom">
				</div>

			</form>

			<div class="am-alert" data-am-alert ng-hide='alertDiv'>
				<button type="button" class="am-close">&times;</button>
				<p>{{msgInfo}}</p>
			</div>

		</div>
	</div>

	<div class="sl-login-coptright">
		<p>© 2017 sula</p>
	</div>
</body>
</html>

