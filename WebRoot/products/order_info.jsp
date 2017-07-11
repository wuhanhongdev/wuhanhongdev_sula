<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="ordController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-archive"></span> 订单信息</strong> / 在此处管理员查看订单，对订单进行操作
	</div>


	<div class="sula-right-content">

		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-id">ID</th>
							<th class="table-title">商品名称</th>
							<th class="table-type">商品积分</th>
							<th class="table-type">订单编号</th>
							<th class="table-type">收货人</th>
							<th class="table-author am-hide-sm-only">电话</th>
							<th class="table-author am-hide-sm-only">地址</th>
							<th class="table-author am-hide-sm-only">状态</th>
							<th class="table-date am-hide-sm-only">订单生成时间</th>
							<th class="table-set">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="20" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td>{{datalist.id}}</td>
							<td>{{datalist.product}}</td>
							<td>{{datalist.points}}</td>
							<td>{{datalist.sn}}</td>

							<td>{{datalist.uname}}</td>
							<td>{{datalist.umobile}}</td>
							<td>{{datalist.uaddress}}</td>

							<td>{{datalist.type==1?'未支付':datalist.type==2?'已支付':datalist.type==3?'已发货':datalist.type==4?'已完成':'--未知--'}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="lookup(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 查看
										</button>
									</div>
									<div class="am-btn-group am-btn-group-xs">
										<button ng-click="doit(datalist.id,datalist.type)"
											class="am-btn am-btn-default am-btn-xs am-text-secondary">
											<span class="am-icon-gear"></span>发货
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
	<!-- 订单信息 -->
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<!--自定义部分-->

			<div class="am-modal-hd">
				<h2>订单详情</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-modal-bd">
				<div class="sl-alert-ts">
					<div class="am-badge am-badge-secondary sl-alert-ts-tag">订单信息/创建时间
						{{data.create_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">订单编号：</strong>{{data.sn}}
						</div>
					</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">商品名称：</strong>{{data.product}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">商品积分：</strong>{{data.points}}
						</div>
					</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">收货人：</strong>{{data.uname}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">联系电话：</strong>{{data.umobile}}
						</div>
					</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">收货地址：</strong>{{data.uaddress}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">订单状态：</strong>{{data.type==1?'未支付':data.type==2?'已支付':data.type==3?'已发货':data.type==4?'已完成':'--未知--'}}
						</div>
					</div>
				</div>
				<div class="sl-alert-ts">
					<div class="am-badge am-badge-secondary sl-alert-ts-tag">发货信息
						{{data.exp_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">物流公司：</strong>{{data.exp}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">物流单号：</strong>{{data.expno}}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
<!-- 物流信息 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>物流信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>


			<!--  -->


			<div class="widget am-cf widget-body-lg am-margin-top-xl">
				<form class="am-form am-form-horizontal">
					
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">物流公司
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="exp" name="exp"
								ng-model="op.exp" placeholder="物流公司名称"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">物流单号</label>
						<div class="am-u-sm-6">
							<input type="text" id="expno" name="expno"
								ng-model="op.expno" placeholder="物流单号"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<hr />
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary" ng-click="modifyObj()" >保存修改</button>
						</div>
					</div>
				</form>
			</div>


		</div>
	</div>

	
</span>