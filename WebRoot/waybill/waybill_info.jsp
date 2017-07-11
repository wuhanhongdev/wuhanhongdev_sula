<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<span ng-controller="wayBill">

	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-file-text"></span> 运单查询</strong> / <small>查询APP用户提交的运单信息</small>
	</div>

	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-7"></div>
			<div class="am-u-sm-12 am-u-md-5">
				<div class="am-input-group">
					<input type="text" class="am-form-field" ng-model="keyword">
					<span class="am-input-group-btn">
						<button class="am-btn am-btn-default" type="button"
							ng-click="search()" placeholder="输入运单号">运单号搜索</button>
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
							<th>运单编号</th>
							<th>运单金额</th>
							<th>下单时间</th>
							<th>支付状态</th>
							<th>支付时间</th>
							<th>运单状态</th>
							<th>操作</th>
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
							<td>{{datalist.code}}</td>
							<td>￥{{datalist.cost}}</td>
							<td>{{datalist.createtime}}</td>
							<td>{{datalist.paystate==0?'待支付':'已支付'}}</td>
							<td>{{datalist.paytime}}</td>
							<td>{{datalist.waystate==1?'运输中':datalist.waystate==2?'待确认':datalist.waystate==3?'已完成':''}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}"
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="lookup(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 查看
										</button>
									</div>
								</div>
							</td>
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
	<div class="am-modal am-modal-alert" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-tabs" data-am-tabs>
					<ul class="am-tabs-nav am-nav am-nav-tabs">
						<li class="am-active"><a href="javascript: void(0)">货源信息</a></li>
						<li><a href="javascript: void(0)">车源信息</a></li>
					</ul>
					<div class="am-tabs-bd">
						<div class="am-tab-panel am-active">
							<div class="am-form-group am-form-group-sm">
								<label for="doc-ipt-3-1" class="am-u-sm-3 am-form-label">状态/state</label>
								<div class="am-u-sm-9"></div>
							</div>

						</div>
						<div class="am-tab-panel"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</span>