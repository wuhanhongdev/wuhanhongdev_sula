<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="inCon">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-truck"></span> 货主发单管理</strong> /管理员对货主发单的管理
	</div>

	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
				<div class="am-btn-toolbar">
					<div class="am-u-sm-12 am-u-md-5">
						<div class="am-input-group">
							<input type="text" class="am-form-field" ng-model="keyword"
								placeholder="请输入车主手机号"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-primary" type="button"
									ng-click="search()" placeholder="请输入手机号码">明细查询</button> </span>
						</div>
					</div>

				</div>


			</div>
		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-title am-hide-sm-only">用户</th>
							<th class="table-title am-hide-sm-only">电话</th>
							<th class="table-title am-hide-sm-only">收入/支出</th>
							<th class="table-title am-hide-sm-only">金额</th>
							<th class="table-date am-hide-sm-only">交易时间</th>
						</tr>
					</thead>



					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td>{{datalist.nick}}</td>
							<td>{{datalist.mobile}}</td>
							<td>{{datalist.type==1?'收入':datalist.type==2?'支出':'其他'}}</td>
							<td>￥{{datalist.money}}</td>
							<td>{{datalist.create_time}}</td>
						</tr>
					</tbody>
				</table>
				<!-- page -->
				<div class="am-panel-footer">
					<div class="am-cf">
						<div class="am-fr">
							<ul class="am-pagination">
								<li class="sl-fenye-panle-panlefoot">共{{count}} 条记录
									当前第{{pageCurrent}}页</li>
								<li><a ng-click='perView(pageCurrent)' href="#">«</a></li>
								<li ng-repeat="pages in pages" ng-class="pageClass(pages)"><a
									ng-click='byNum(pages.pageNum)' href="#">{{pages.pageNum}}</a>
								</li>
								<li><a ng-click='next(pageCurrent)' href="#">»</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> <!--  用户信息--> </span>