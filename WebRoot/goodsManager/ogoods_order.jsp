<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="odrCon">
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
								<button class="am-btn am-btn-default" type="button"
									ng-click="search()" placeholder="请输入手机号码">发单查询</button> </span>
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
							<th class="table-title am-hide-sm-only">货物</th>
							<th class="table-title am-hide-sm-only">重量</th>
							<th class="table-title am-hide-sm-only">体积</th>
							<th class="table-title am-hide-sm-only">系统价格</th>
							<th class="table-date am-hide-sm-only">货主</th>
							<th class="table-date am-hide-sm-only">电话</th>
							<th class="table-date am-hide-sm-only">留言</th>
							<th class="table-type am-hide-sm-only">装货地</th>
							<th class="table-type am-hide-sm-only">卸货地</th>
							<th class="table-date am-hide-sm-only">装车日期</th>
							<th class="table-date am-hide-sm-only">发单日期</th>
							<th class="table-date am-hide-sm-only">当前状态</th>

						</tr>
					</thead>



					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td>{{datalist.goods}}</td>
							<td>{{datalist.weights}}</td>
							<td>{{datalist.dulk}}</td>


							<td>{{datalist.freight}}</td>
							<td>{{datalist.nick}}</td>
							<td>{{datalist.mobile}}</td>

							<td>{{datalist.message}}</td>
							<td>{{datalist.startplace}}</td>
							<td>{{datalist.endplace}}</td>

							<td>{{datalist.loadtime}}</td>
							<td>{{datalist.create_time}}</td>


							<td>{{datalist.type==1?'发布':datalist.type==2?'撮合':datalist.type==3?'成交':datalist.type==4?'失效':datalist.type==5?'完成':'其他'}}</td>
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