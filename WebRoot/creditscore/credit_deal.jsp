<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<span ng-controller="creditDealController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-puzzle-piece"></span> 失信处理</strong> / <small>查看APP用户{{creditState==1?'':creditState==2?'警戒线':creditState==3?'禁止信息发布':creditState==4?'禁止接单':''}}的信用情况</small>
	</div>
	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-9 am-u-md-9">
				<div class="am-btn-toolbar">
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-success"
							ng-click="changeState(1)">
							<span class="am-icon-archive"></span> 帐号解禁
						</button>
						<button type="button" class="am-btn am-btn-danger"
							ng-click="changeState(2)">
							<span class="am-icon-trash-o"></span> 帐号封停
						</button>
					</div>
				</div>
			</div>
			<div class="am-u-sm-3 am-u-md-3">
				<div class="am-form-group">
					<select id="doc-select-1" ng-change="change()"
						ng-model="creditState">
						<option value="1" selected="selected">全部</option>
						<option value="2">警戒线</option>
						<option value="3">禁止信息发布</option>
						<option value="4">禁止接单</option>
					</select> <span class="am-form-caret"></span>
				</div>
			</div>
		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-check"></th>
							<th>帐号状态</th>
							<th>用户</th>
							<th>手机</th>
							<th>信用积分</th>
							<th>信息发布状态</th>
							<th>接单状态</th>

						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="20" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td><input type="checkbox" ng-checked="select_one"
								ng-click="selectOne(datalist.id)" /></td>
							<td><span
								ng-class="datalist.state==1?'am-badge am-badge-primary':'am-badge am-badge-danger'">{{datalist.state==1?'正常':'封停'}}</span></td>
							<td>{{datalist.nick}}</td>
							<td>{{datalist.mobile}}</td>
							<td>{{datalist.credit}}</td>
							<td>{{datalist.ispublish==1?'正常':'禁止信息发布'}}</td>
							<td>{{datalist.isorder==1?"正常":'禁止接单'}}</td>
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