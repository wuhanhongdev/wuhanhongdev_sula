<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<span ng-controller="carCon">

	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-file-text"></span> 公司车辆</strong> / <small>查询公司车辆的信息</small>
	</div>

	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-7"></div>
			<div class="am-u-sm-12 am-u-md-5">
				<div class="am-input-group">
					<input type="text" class="am-form-field" ng-model="keyword">
					<span class="am-input-group-btn">
						<button class="am-btn am-btn-default" type="button"
							ng-click="search()" placeholder="输入车牌号">车牌号搜索</button>
					</span>
				</div>
			</div>
		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-check"></th>
							<th>车型</th>
							<th>车牌</th>
							<th>位置起</th>
							<th>位置止</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td><input type="checkbox" ng-checked="select_one"
								ng-click="selectOne(datalist.id)" /></td>
							<td>{{datalist.carmodels}}</td>
							<td>{{datalist.plateno}}</td>
							<td>{{datalist.startplace}}</td>
							<td>{{datalist.endplace}}</td>
							<td>{{datalist.waystate==1?'运输中':datalist.waystate==2?'待确认':datalist.waystate==3?'已完成':'其他'}}</td>
						</tr>
					</tbody>
				</table>
				<div class="am-panel-footer">
					<div class="am-cf">
						<div class="am-fr">
							<ul class="am-pagination">
								<li class="sl-fenye-panle-panlefoot">共{{count}} 条记录
									当前第{{pageCurrent}}页</li>
								<li><a ng-click='perView(pageCurrent)' href="#">«</a></li>
								<li ng-repeat="pages in pages" ng-class="pageClass(pages)"><a
									ng-click='byNum(pages.pageNum)' href="#">{{pages.pageNum}}</a></li>
								<li><a ng-click='next(pageCurrent)' href="#">»</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</span>