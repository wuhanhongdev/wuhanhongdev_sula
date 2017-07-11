<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<span ng-controller="goodsController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-cube"></span> 货源投诉</strong> / <small>管理员对APP用户的投诉处理</small>
	</div>
	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-check"></th>
							<th class="table-id">ID</th>
							<th class="table-title">投诉人</th>
							<th class="table-type">投诉人电话</th>
							<th class="table-author am-hide-sm-only">处理状态</th>
							<th class="table-author am-hide-sm-only">投诉时间</th>
							<th class="table-set">操作</th>
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
							<td>{{datalist.id}}</td>
							<td>{{datalist.tsr}}</td>
							<td>{{datalist.mobile}}</td>
							<td>{{datalist.state==0?'未处理':datalist.state==1?'已处理':'未知'}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="lookup(datalist.id,datalist.source_id
											)">
											<span class="am-icon-pencil-square-o"></span> 查看
										</button>
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="dispost(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 处理
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
									ng-click='byNum(pages.pageNum)' href="#">{{pages.pageNum}}</a>
								</li>
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
				<h2>投诉详情</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-modal-bd">
				<div class="sl-alert-ts">
					<div class="am-badge am-badge-secondary sl-alert-ts-tag">投诉日期
						{{data.create_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">投诉人：</strong>{{data.tsr}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">联系方式：</strong>{{data.mobile}}
						</div>
					</div>

					<div class="am-g am-margin-top">
						<div class="am-u-sm-3 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">投诉内容：</strong>
						</div>
						<div class="am-u-sm-9 am-text-left">
							<p class="am-text-left">{{data.tscont}}</p>
						</div>
					</div>
				</div>

				<div class="sl-alert-ts">
					<div class="am-badge am-badge-warning sl-alert-ts-tag">被投诉人信息</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">被投诉人：</strong>{{data.nick}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">联系方式：</strong>{{data.tel}}
						</div>
					</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">货物名称：</strong>{{data.goods}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">重量：</strong>{{data.freight}}
						</div>
					</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">装货地：</strong>{{data.startplace}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">卸货地：</strong>{{data.endplace}}
						</div>
					</div>
				</div>

				<div class="sl-alert-ts">
					<div class="am-badge am-badge-success sl-alert-ts-tag">处理日期
						{{data.finish_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-3 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">处理内容：</strong>
						</div>
						<div class="am-u-sm-9 am-text-left">
							<p class="am-text-left">{{data.clcont}}</p>
						</div>

					</div>
				</div>


			</div>
		</div>
	</div>


	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				投诉处理 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<form class="am-form am-form-horizontal">
				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">是否在APP显示</div>
							<div class="am-u-sm-8 am-u-md-10">
								<select data-am-selected="{btnSize: 'sm'}"
									ng-model="comp.selectedSite">
									<option value="1">显示</option>
									<option value="0">不显示</option>
								</select>
							</div>
						</div>
						<div class="am-form-group am-form-group-sm">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">处理意见</div>
							<div class="am-u-sm-10">
								<textarea class="" rows="5" id="doc-ta-1" ng-model="comp.clcont"></textarea>
							</div>
						</div>
						<div class="am-form-group am-form-group-sm">
							<div class="am-u-sm-12">
								<button type="button" class="am-btn am-btn-primary"
									ng-click="submitComp()">提交</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</span>