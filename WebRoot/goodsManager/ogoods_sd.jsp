<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="sdCons">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-truck"></span> 货源匹配</strong> /管理员对发布中的货源，进行手动匹配
	</div>

	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
				<div class="am-btn-toolbar">
					
					 <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="companyCar()">
                    <span class="am-icon-archive"></span> 公司车辆【派单】
                  </button>
                </div>
               	
               	
               	 <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="societyCar()">
                    <span class="am-icon-bell"></span> 社会车辆【派单】
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
                    ng-click="selectOne(datalist.id,datalist.mobile)" /></td>
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
				
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th>车主</th>
							<th>电话</th>
							<th>常驻地址</th>
							<th>车型</th>
							<th>车牌</th>
							<th>载重</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="car in car" >
							<td>{{car.nick}}</td>
							<td>{{car.mobile}}</td>
							<td>{{car.address}}</td>
							<td>{{car.carmodels}}</td>
							<td>{{car.plateno}}</td>
							<td>{{car.carload}}</td>
							<td>
								<div class="am-btn-toolbar">
                      <div class="am-btn-group am-btn-group-xs">
                        <button
                          data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 400, height: 225}"
                          class="am-btn am-btn-default am-btn-xs am-text-secondary"
                          ng-click="cdp(car.hy_id,car.hy_tel,car.mobile,car.id)">
                          <span class="am-icon-pencil-square-o"></span> 匹配
                        </button>
                      </div>
                    </div>
							
							
							</td>
						</tr>
					</tbody>
				</table>
				
				
				
				
			</div>


		</div>
	</div>
	 
	 <!-- 社会车辆 -->
	 
	 
	 	 
	 <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>社会车辆信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<!--  -->
			<div class="widget am-cf widget-body-lg am-margin-top-xl">
				
				<form class="am-form am-form-horizontal"">
					<input type="hidden" id="id" name="id" ng-model="temp.id" />
					
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">货主手机号码
						</label>
						<div class="am-u-sm-6">
							{{temp.mobile}}
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
					
					
					
					
					
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">车主手机号码
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="tel" name="tel"
								ng-model="temp.tel" placeholder="车主手机号码"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					
			


					<hr />
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary"
								ng-click="createion()" ng-hide='subButton'>匹配</button>
						</div>
					</div>
				</form>
				
				
				
				
			</div>


		</div>
	</div>
	  
</span>