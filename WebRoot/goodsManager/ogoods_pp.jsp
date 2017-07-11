<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="ppCon">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-truck"></span> 货源信息</strong> /管理员对货源进行维护
	</div>

	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
				<div class="am-btn-toolbar">
					
					 <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="add()">
                    <span class="am-icon-archive"></span> 添加货源
                  </button>
                </div>
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-romantic" ng-click="update()">
                    <span class="am-icon-exclamation-triangle"></span> 修改货源
                </button>
              </div>
					
					
					
					
					<div class="am-u-sm-12 am-u-md-5">
						<div class="am-input-group">
							<input type="text" class="am-form-field" ng-model="keyword"
								placeholder="请输入车主手机号"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-primary" type="button"
									ng-click="search()" placeholder="请输入手机号码">货源查询</button> </span>
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
						  	<th class="table-check"></th>
							<th class="table-title am-hide-sm-only">货物</th>
							<th class="table-title am-hide-sm-only">重量/KG</th>
							<th class="table-title am-hide-sm-only">体积/m³</th>
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
						 <td><input type="checkbox" ng-checked="select_one"
                    ng-click="selectOne(datalist.id)" /></td>
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
	</div>
	
	
	
	 <!--  货源信息-->
	 
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
						<label for="user-name" class="am-u-sm-3 am-form-label">货主
						</label>
						<div class="am-u-sm-6">
						
						<input type="text" id="mobile" name="mobile"
								ng-model="data.mobile" placeholder="请填写货主手机号码"> <small></small>
						
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					
					
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">货物名称
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="goods" name="goods"
								ng-model="data.goods" placeholder="请填写货物名称"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">货物重量</label>
						<div class="am-u-sm-6">
							<input type="text" id="weights" name="weights"
								ng-model="data.weights" placeholder="请填写货物重量，单位吨"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">商品体积</label>
						<div class="am-u-sm-6">
							<input type="text" id="dulk" name="dulk"
								ng-model="data.dulk" placeholder="请填写货物体积，单位立方米"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">装货地点</label>
						<div class="am-u-sm-6">
							<input type="text" id="startplace" name="startplace"
								ng-model="data.startplace" placeholder="请填写装货地点"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">卸货地点</label>
						<div class="am-u-sm-6">
							<input type="text" id="endplace" name="endplace"
								ng-model="data.endplace" placeholder="请填写卸货地点"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>


					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">装车时间</label>
						<div class="am-u-sm-6">
							<input type="text" id="loadtime" name="loadtime" ng-model="data.loadtime"
								placeholder="请填写装车时间 比如 早8：00 - 晚 20：00"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">希望车型</label>
						<div class="am-u-sm-6">
							<input type="text" id="carmodels" name="carmodels" ng-model="data.carmodels"
								placeholder="请填写希望运输车辆的型号"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">希望车长</label>
						<div class="am-u-sm-6">
							<input type="text" id="carlength" name="carlength" ng-model="data.carlength"
								placeholder="请填写希望运输车辆的长度，单位米"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">运输费用</label>
						<div class="am-u-sm-6">
							<input type="text" id="freight" name="freight" ng-model="data.freight"
								placeholder="请填写运输费用 单位元"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					
					<div class="am-form-group">
						<label for="user-email" class="am-u-sm-3 am-form-label">给司机留言</label>
						<div class="am-u-sm-6">
							<input type="text" id="message" name="message" ng-model="data.message"
								placeholder="给司机留言"> <small></small>
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