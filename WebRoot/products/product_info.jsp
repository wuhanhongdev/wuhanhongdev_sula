<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="proController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-archive"></span> 商品信息</strong> / 积分商城的商品，APP用户可用积分换购
	</div>


	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
				<div class="am-btn-toolbar">
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-sula"
							ng-click="addpro()">
							<span class="am-icon-plus"></span> 添加商品
						</button>
					</div>
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-sula"
							ng-click="updatepro()">
							<span class="am-icon-star"></span> 修改商品
						</button>
					</div>
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-sula"
							ng-click="delpro()">
							<span class="am-icon-trash"></span> 删除商品
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th class="table-check"></th>
							<th class="table-id">ID</th>
							<th class="table-title">商品名称</th>
							<th class="table-type">商品积分</th>
							<th class="table-type">商品数量</th>
							<th class="table-author am-hide-sm-only">是否上架</th>
							<th class="table-date am-hide-sm-only">创建日期</th>
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
							<td>{{datalist.product}}</td>
							<td>{{datalist.points}}</td>
							<td>{{datalist.nums}}</td>
							<td>{{datalist.is_show==1?'上架':datalist.is_show==0?'下架':'未知状态'}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											data-am-modal="{target: '#doc-modal-1',  width: 1000, height:800}"
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="lookup(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 查看
										</button>
									</div>
									<div class="am-btn-group am-btn-group-xs">
										<button ng-click="doit(datalist.id)"
											class="am-btn am-btn-default am-btn-xs am-text-secondary">
											<span class="am-icon-gear"></span> 上/下架
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
			<div class="am-modal-hd">
				<h2>详细信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>


			<!--  -->


			<div class="widget am-cf widget-body-lg am-margin-top-xl">
				<form class="am-form am-form-horizontal"">
					<input type="hidden" id="id" name="id" ng-model="data.id" />
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">商品名称
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="product" name="product"
								ng-model="data.product" placeholder="商品名称"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">商品积分</label>
						<div class="am-u-sm-6">
							<input type="text" id="points" name="points"
								ng-model="data.points" placeholder="商品积分"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">商品描述</label>
						<div class="am-u-sm-6">
							<input type="text" id="bewrite" name="bewrite"
								ng-model="data.bewrite" placeholder="商品描述"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>


					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">商品数量</label>
						<div class="am-u-sm-6">
							<input type="text" id="nums" name="nums" ng-model="data.nums"
								placeholder="商品数量"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group" ng-hide="proDiv">
						<div class="am-u-sm-9">
							<div class="am-form-group am-form-file">
								<button type="button" class="am-btn am-btn-danger am-btn-sm">
									<i class="am-icon-cloud-upload"></i> 上传商品的照片
								</button>
								<input id="img" name="img" type="file">
							</div>
						</div>
					</div>
					<div class="am-form-group" ng-hide='imgDiv'>
						<label for="user-email" class="am-u-sm-3 am-form-label">图片展示</label>
						<div class="am-u-sm-6">
							<div class="tpl-form-file-img">
								<img class="am-img-responsive"
									ng-src="<%=basePath%>proImgs/{{data.img}}" />
							</div>
						</div>
						<div class="am-u-sm-3"></div>
					</div>

					<hr />
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary"
								ng-click="modifyObj()" ng-hide='subButton'>保存修改</button>
						</div>
					</div>
				</form>
			</div>


		</div>
	</div>
</span>