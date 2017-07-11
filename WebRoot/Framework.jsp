<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html class="no-js fixed-layout">
<head>
<title>首页</title>
<meta name="description" content="首頁">
<meta name="keywords" content="index">
<%@ include file="/comm_css_libs.jsp"%>
<%@ include file="/comm_js_libs.jsp"%>
<style type="text/css">
.admin-sidebar {
	background:#0776d0 url(assets/css/bg-bot.jpg) no-repeat;
	background-position:bottom;
}
</style>
</head>
<body data-type="index" ng-app="myApp" ng-controller="myCtrl" >

	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器， 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand am-animation-slide-left">
			<strong>速拉</strong> <small>后台管理系统</small>
		</div>
		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-primary am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>
		<div class="am-collapse am-topbar-collapse am-animation-slide-right"
			id="topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
				<!-- <li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span class="am-badge am-badge-warning">5</span></a></li> -->
				<li class="am-dropdown" data-am-dropdown=""><a
					class="am-dropdown-toggle" data-am-dropdown-toggle=""
					href="javascript:;"> <span class="am-icon-users"></span>
						${USERINFO.username } <span class="am-icon-caret-down"></span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
						<li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
						<li><a href="<%=basePath%>"><span
								class="am-icon-power-off"></span> 退出</a></li>
					</ul></li>
				<li class="am-hide-sm-only"><a href="javascript:;"
					id="admin-fullscreen"><span class="am-icon-arrows-alt"></span>
						<span class="admin-fullText">开启全屏</span></a></li>
			</ul>
		</div>
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<%-- <div class="am-panel am-panel-default admin-sidebar-panel">
					<div class="am-panel-bd">
						<img class="am-img-thumbnail am-circle" src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/1000/h/1000/q/80" width="50" height="50"/>
						&nbsp;&nbsp;&nbsp;&nbsp;${USERINFO.username }
					</div>
				</div> --%>
				<ul class="am-list admin-sidebar-list" style="">
					<li><a ui-sref="view" href="javasript:void(0)"><span
							class="am-icon-home"></span> 首页</a></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#checkInfo'}" ng-click="selectM(1)">
							<span class="am-icon-newspaper-o"></span> 认证审核 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_1]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="checkInfo">
							<li><a ui-sref="owner" class="am-cf" ng-click="selectedA(1)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num1]">
									<span class="am-icon-truck"></span> 车主审核
							</a></li>
							<li><a ui-sref="source" class="am-cf"
								ng-click="selectedA(2)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num2]">
									<span class="am-icon-cube"></span> 货源审核
							</a></li>
						</ul></li>
						<!--  新增加部分  车主管理-->
						
						<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#ucInfo'}" ng-click="selectM(8)">
							<span class="am-icon-commenting-o"></span> 车主管理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_8]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="ucInfo">
							<li><a ui-sref="carowners" class="am-cf" ng-click="selectedA(16)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num16]">
									<span class="am-icon-modx"></span> 车主信息
							</a></li>
							<li><a ui-sref="cars" class="am-cf"
								ng-click="selectedA(17)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num17]">
									<span class="am-icon-truck"></span> 车辆信息
							</a></li>
						</ul></li>
						
						
						<!-- 新增货主信息 -->
						
						<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#gInfo'}" ng-click="selectM(9)">
							<span class="am-icon-gg"></span> 货主管理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_9]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="gInfo">
							<li><a ui-sref="hz" class="am-cf" ng-click="selectedA(18)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num18]">
									<span class="am-icon-map"></span> 货主信息
							</a></li>
							<li><a ui-sref="fd" class="am-cf"
								ng-click="selectedA(19)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num19]">
									<span class="am-icon-tv"></span> 发单信息
							</a></li>
							
							<li><a ui-sref="sr" class="am-cf"
								ng-click="selectedA(20)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num20]">
									<span class="am-icon-cc"></span> 收入信息
							</a></li>
							
							<li><a ui-sref="hrpp" class="am-cf"
								ng-click="selectedA(21)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num21]">
									<span class="am-icon-cc"></span> 货源信息
							</a></li>
							
							<li><a ui-sref="sdpp" class="am-cf"
								ng-click="selectedA(22)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num22]">
									<span class="am-icon-chain-broken"></span> 货源匹配
							</a></li>
							
							
						</ul></li>
					<!-- 车辆管理 -->
					
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#carso'}" ng-click="selectM(10)">
							<span class="am-icon-bus"></span> 车辆管理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_10]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="carso">
							<li><a ui-sref="cas" class="am-cf" ng-click="selectedA(23)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num23]">
									<span class="am-icon-map"></span> 车辆信息
							</a></li>
							
							
							
							
							
						</ul></li>
					
					
					
					
					
					<!--  -->
					
						
						
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#comInfo'}" ng-click="selectM(2)">
							<span class="am-icon-envelope"></span> 投诉处理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_2]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="comInfo">
							<li><a ui-sref="goods" class="am-cf" ng-click="selectedA(3)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num3]">
									<span class="am-icon-cube"></span> 货源投诉
							</a></li>
							<li><a ui-sref="trucks" class="am-cf"
								ng-click="selectedA(4)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num4]">
									<span class="am-icon-truck"></span> 车源投诉
							</a></li>
						</ul></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#contentsInfo'}" ng-click="selectM(3)">
							<span class="am-icon-folder"></span> 内容管理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_3]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub"
							id="contentsInfo">
							<li><a ui-sref="adv" class="am-cf" ng-click="selectedA(5)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num5]">
									<span class="am-icon-picture-o"></span> 广告管理
							</a></li>
							<li><a ui-sref="consult" class="am-cf"
								ng-click="selectedA(6)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num6]">
									<span class="am-icon-newspaper-o"></span> 资讯管理
							</a></li>
							<li><a ui-sref="assist" class="am-cf"
								ng-click="selectedA(7)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num7]">
									<span class="am-icon-tags"></span> 帮助信息
							</a></li>
						</ul></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#topInfo'}" ng-click="selectM(4)">
							<span class="am-icon-share-alt"></span> 信息推广 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_4]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="topInfo">
							<li><a ui-sref="cartop" class="am-cf"
								ng-click="selectedA(8)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num8]">
									<span class="am-icon-truck"></span> 车源置顶
							</a></li>
							<li><a ui-sref="goodstop" class="am-cf"
								ng-click="selectedA(9)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num9]">
									<span class="am-icon-cube"></span> 货源置顶
							</a></li>
						</ul></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#payInfo'}" ng-click="selectM(5)">
							<span class="am-icon-credit-card-alt"></span> 支付信息 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_5]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="payInfo">
							<li><a ui-sref="waybill" href="javascript:void(0)"
								class="am-cf" ng-click="selectedA(10)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num10]">
									<span class="am-icon-file-text"></span> 运单支付信息
							</a></li>
							<li><a ui-sref="cashout" href="javascript:void(0)"
								class="am-cf" ng-click="selectedA(11)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num11]">
									<span class="am-icon-jpy"></span> 提现申请
							</a></li>
						</ul></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#csl-menue-normalitInfo'}"
						ng-click="selectM(6)"> <span class="am-icon-diamond"></span>
							信用积分 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_6]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub"
							id="csl-menue-normalitInfo">
							<li><a ui-sref="irule" class="am-cf"
								ng-click="selectedA(12)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num12]">
									<span class="am-icon-book"></span> 积分规则
							</a></li>
							<li><a ui-sref="creditdeal" ng-click="selectedA(13)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num13]">
									<span class="am-icon-puzzle-piece"></span> 失信处理
							</a></li>
						</ul></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#scInfo'}" ng-click="selectM(7)">
							<span class="am-icon-shopping-basket"></span> 积分商城 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_7]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="scInfo">
							<li><a ui-sref="products" class="am-cf"
								ng-click="selectedA(14)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num14]">
									<span class="am-icon-archive"></span> 商品管理
							</a></li>
							<li><a ui-sref="orders" class="am-cf"
							    ng-click="selectedA(15)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num15]">
									<span class="am-icon-wpforms"></span> 订单发货
							</a></li>
							
							<li><a ui-sref="refund" class="am-cf"
							    ng-click="selectedA(24)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num24]">
									<span class="am-icon-money"></span> 订单退款
							</a></li>
							
						</ul></li>
						
						<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#jpInfo'}" ng-click="selectM(11)">
							<span class="am-icon-map-signs"></span> 消息管理 <span
							ng-class="{true: 'am-icon-angle-down', false: 'am-icon-angle-right'}[index_7]"
							class="am-fr am-margin-right"></span>
					</a>
						<ul class="am-list am-collapse admin-sidebar-sub" id="jpInfo">
							<li><a ui-sref="jpm" class="am-cf"
								ng-click="selectedA(25)"
								ng-class="{true: 'sl-menue-active',false:'sl-menue-normal'}[num25]">
									<span class="am-icon-file"></span> 消息内容
							</a></li>
							
							
						</ul></li>
						
						
						
					<li><a href="admin-table.html"> <span
							class="am-icon-bar-chart"></span> 统计信息
					</a></li>
				</ul>


			</div>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div ui-view=""></div>
		<div class="admin-content sula-right-box">
			<div class="admin-content-body" ui-view="front">
				<!-- front page -->
			</div>
			<!--  -->
			<footer class="admin-content-footer" ui-view="footer">
				<!-- footer page -->
			</footer>
		</div>

		<!-- content end -->

	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
</body>
</html>

