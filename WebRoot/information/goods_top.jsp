<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="goodstopController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-cube"></span> 货源信息</strong> / <small>管理员对APP用户发布的货源信息进行推荐</small>
	</div>
	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-id">ID</th>
							<th class="table-title">货物名称</th>
							<th class="table-type">货主</th>
							<th class="table-type">联系方式</th>
							<th class="table-type">是否置顶</th>
							<th class="table-author am-hide-sm-only">发布时间</th>
							<th class="table-set">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td>{{datalist.id}}</td>
							<td>{{datalist.goods}}</td>
							<td>{{datalist.nick}}</td>
							<td>{{datalist.mobile}}</td>
							<td>{{datalist.is_top==0?'默认':datalist.is_top==1?'置顶':'未知'}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="lookup(datalist.id,datalist.trucks_id
											)">
											<span class="am-icon-pencil-square-o"></span> 查看
										</button>
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="dispost(datalist.id,datalist.is_top)">
											<span class="am-icon-pencil-square-o"></span> 置顶/取消
										</button>
									</div>
								</div>
							</td>
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
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<!--自定义部分-->

			<div class="am-modal-hd">
				<h2>货源详情</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-modal-bd">
				<div class="sl-alert-ts">
					<div class="am-badge am-badge-secondary sl-alert-ts-tag">货源信息
						{{data.create_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">货主：</strong>{{data.nick}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">电话：</strong>{{data.mobile}}
						</div>
					</div>

					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">货物：</strong>{{data.goods}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">重量：</strong>{{data.weights}}
						</div>
					</div>

					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">装货时间：</strong>{{data.loadtime}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">体积：</strong>{{data.dulk}}
						</div>
					</div>

					<div class="am-g am-margin-top">

						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">出发地：</strong>{{data.startplace}}
						</div>

						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">目的地：</strong>{{data.endplace}}
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</span>