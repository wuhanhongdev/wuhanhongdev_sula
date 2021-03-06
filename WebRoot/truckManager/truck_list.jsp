<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="trucksc">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-truck"></span> 车辆信息</strong> / <small>管理员对APP用户发布的车辆信息进行维护</small>
	</div>
	<div class="sula-right-content">
		
		
			<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
				<div class="am-btn-toolbar">
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-sula"
							ng-click="addUser()">
							<span class="am-icon-plus"></span> 添加车辆
						</button>
					</div>

				

					


					<div class="am-u-sm-12 am-u-md-5">
						<div class="am-input-group">
							<input type="text" class="am-form-field" ng-model="keyword"
								placeholder="请输入车主手机号"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" type="button"
									ng-click="search()" placeholder="请输入手机号码">查询</button> </span>
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
							<th class="table-type">车主</th>
							<th class="table-title">车型号</th>
							<th class="table-title">车牌</th>
							<th class="table-title">车长</th>
							<th class="table-title">载重</th>
							<th class="table-title">常驻地址</th>
							<th class="table-author am-hide-sm-only">添加时间</th>
							<th class="table-set">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist" ng-class="rowClass(datalist)">
							<td>{{datalist.nick}}</td>
							<td>{{datalist.carmodels}}</td>
							<td>{{datalist.plateno}}</td>
							<td>{{datalist.carlenght}}</td>
							<td>{{datalist.carload}}</td>
							<td>{{datalist.address}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
									
									<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="dispost(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 修改
										</button>
									
										<button
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="del(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 删除
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
				<h2>车辆信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			
				<div class="widget am-cf widget-body-lg am-margin-top-xl">
				<form class="am-form am-form-horizontal"">
					<input type="hidden" id="id" name="id" ng-model="data.id" />
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">车主手机
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="mobile" name="mobile"
								ng-model="data.mobile" placeholder="填写车主手机号码"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">车辆型号</label>
						<div class="am-u-sm-6">
							<input type="text" id="carmodels" name="carmodels"
								ng-model="data.carmodels" placeholder="填写车辆型号"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">车辆长度</label>
						<div class="am-u-sm-6">
							<input type="text" id="carlength" name="carlength"
								ng-model="data.carlength" placeholder="填写车辆长度"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">车牌号码</label>
						<div class="am-u-sm-6">
							<input type="text" id="plateno" name="plateno" ng-model="data.plateno"
								placeholder="填写车牌号码"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">车辆载重</label>
						<div class="am-u-sm-6">
							<input type="text" id="carload" name="carload" ng-model="data.carload"
								placeholder="填写车辆载重"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">常驻地址</label>
						<div class="am-u-sm-6">
							<input type="text" id="address" name="address" ng-model="data.address"
								placeholder="填写车辆常驻地址"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group" ng-hide="proDiv">
						<div class="am-u-sm-9">
							<div class="am-form-group am-form-file">
								<button type="button" class="am-btn am-btn-danger am-btn-sm">
									<i class="am-icon-cloud-upload"></i> 上传车辆照片
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
									ng-src="<%=basePath%>truckImgs/{{data.img}}" />
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
	</div>
</span>