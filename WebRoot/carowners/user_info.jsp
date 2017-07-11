<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="userController">
  <div class="sula-right-title">
      <strong class="am-text-primary title-text"><span class="am-icon-truck"></span> 车主管理</strong> /管理员对车主的管理
  </div>

  <div class="sula-right-content">
        <div class="am-g">
          <div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
            <div class="am-btn-toolbar">
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="addUser()">
                    <span class="am-icon-plus"></span> 添加车主
                  </button>
                </div>
                
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="updateUser()">
                    <span class="am-icon-reply"></span> 修改车主
                  </button>
                </div>
                
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="gerUserCar()">
                    <span class="am-icon-car"></span> 拥有车辆
                  </button>
                </div>
                
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="getWaybill()">
                    <span class="am-icon-flag"></span> 接单信息
                  </button>
                </div>
                
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="gerPurseInfo()">
                    <span class="am-icon-jpy"></span> 收入信息
                  </button>
                </div>
                
                
                 <div class="am-u-sm-12 am-u-md-5">
				<div class="am-input-group">
					<input type="text" class="am-form-field" ng-model="keyword"  placeholder="请输入车主手机号">
					<span class="am-input-group-btn">
						<button class="am-btn am-btn-default" type="button"
							ng-click="search()" placeholder="输入运单号">车住查询</button>
					</span>
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
                  <th class="table-id">ID</th>
                  <th class="table-title">用户</th>
                  <th class="table-type">手机</th>
                  <th class="table-author am-hide-sm-only">状态</th>
                  <th class="table-date am-hide-sm-only">提交日期</th>
                  <th class="table-date am-hide-sm-only">车主属性</th>
                  <th class="table-date am-hide-sm-only">信用分值</th>
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
                  <td>{{datalist.nick}}</td>
                  <td>{{datalist.mobile}}</td>
                  <td>{{datalist.type==1?'待审核':datalist.type==2?'审核通过':datalist.type==3?'审核驳回':'未知状态'}}</td>
                  <td>{{datalist.create_time}}</td>
                  <td>{{datalist.attach == 1 ? '社会车主' : datalist.attach == 2 ? '公司车主' : '其他'}}</td>
                   <td>{{datalist.credit}}</td>
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
                        <button
                          class="am-btn am-btn-default am-btn-xs am-text-secondary"
                          ng-click="blackm(datalist.id)">
                          <span class="am-icon-pencil-square-o"></span> 拉黑
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
  
<!--  用户信息-->  

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>车主信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>


			<!--  -->


			<div class="widget am-cf widget-body-lg am-margin-top-xl">
				<form class="am-form am-form-horizontal">
					<input type="hidden" id="id" name="id" ng-model="data.id" />
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">车主手机
						</label>
						<div class="am-u-sm-6">
							<input type="text" id="mobile" name="mobile"
								ng-model="data.mobile" placeholder="车主手机"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">车主昵称</label>
						<div class="am-u-sm-6">
							<input type="text" id="nick" name="nick"
								ng-model="data.nick" placeholder="车主昵称"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>
					<div class="am-form-group">
						<label for="user-name" class="am-u-sm-3 am-form-label">登录密码</label>
						<div class="am-u-sm-6">
							<input type="password" id="password" name="password"
								ng-model="data.password" placeholder="登录密码"> <small></small>
						</div>
						<div class="am-u-sm-3"></div>
					</div>

					<div class="am-form-group" ng-hide="proDiv">
						<div class="am-u-sm-9">
							<div class="am-form-group am-form-file">
								<button type="button" class="am-btn am-btn-danger am-btn-sm">
									<i class="am-icon-cloud-upload"></i> 上传头像
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
									ng-src="<%=basePath%>UserImgs/{{data.img}}" />
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


<!--车辆列表  -->


	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>车辆信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>


			<!--  -->


			<div class="widget am-cf widget-body-lg am-margin-top-xl">
				<table class="am-table am-table-striped am-table-hover table-main">
              <thead>
                <tr>
                  <th class="table-title">车型</th>
                  <th class="table-title">车长</th>
                  <th class="table-title">车牌</th>
                  <th class="table-title">载重</th>
                  <th class="table-title">常驻地址</th>
                </tr>
              </thead>
              <tbody>
                <tr ng-show="showTr">
                  <td colspan="8" align="center" scope="row"
                    class="null_information">※没有找到相关数据!</td>
                </tr>
                <tr ng-repeat="car in cars" ng-class="rowClass(datalist)">
                  <td>{{car.carmodels}}</td>
                  <td>{{car.carlength}}</td>
                  <td>{{car.plateno}}</td>
                  <td>{{car.carload}}</td>
                  <td>{{car.address}}</td>
                </tr>
              </tbody>
            </table>
			</div>
		</div>
	</div>
<!--  -->



<!-- 运单列表 -->


<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-3">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>车辆信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>


			<!--  -->


			    <div class="am-u-sm-12">
            <table class="am-table am-table-striped am-table-hover table-main">
              <thead>
                <tr>
                  <th class="table-title">运单金额</th>
                  <th class="table-type">货主下单时间</th>
                  <th class="table-author am-hide-sm-only">货主支付时间</th>
                  <th class="table-date am-hide-sm-only">运单状态</th>
                </tr>
              </thead>
              <tbody>
                <tr ng-repeat="way in ways" ng-class="rowClass(datalist)">
                  <td>{{way.cost}}</td>
                  <td>{{way.createtime}}</td>
                  <td>{{way.paytime}}</td>
                  <td>{{way.waystate == 1 ? '运输中' : way.waystate == 2 ? '待确认' :  way.waystate == 3 ? '已完成' : '其他'}}</td>
                </tr>
              </tbody>
            </table>
            <!-- page -->
            <div class="am-panel-footer">
              <div class="am-cf">
                <div class="am-fr">
                  <ul class="am-pagination">
                    <li class="sl-fenye-panle-panlefoot">共{{num}} 条记录
                      当前第{{pagec}}页</li>
                    <li><a ng-click='perView(pagec)' href="#">«</a></li>
                    <li ng-repeat="pn in pn" ng-class="pageClass(pn)"><a
                      ng-click='byNum(pn.pageNum)' href="#">{{pn.pageNum}}</a></li>
                    <li><a ng-click='next(pagec)' href="#">»</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
		</div>
	</div>


<!--  -->

<!-- 收入信息 -->

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-4">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<h2>车辆信息</h2>
				<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-modal-bd">
				<div class="sl-alert-ts">
					<div class="am-badge am-badge-secondary sl-alert-ts-tag">查看日期
						{{purse.create_time}}</div>
					<div class="am-g am-margin-top">
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">当前总收入：</strong>{{purse.sr}}
						</div>
						<div class="am-u-sm-6 am-text-left">
							<strong style="width:200px; display:inline-block"
								class="am-text-right">当前总支出：</strong>{{purse.zc}}
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

<!--  -->





</span>