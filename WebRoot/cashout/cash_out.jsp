<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="cashOut">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-jpy"></span> 提现申请</strong> / <small>处理APP用户提交的提现申请</small>
	</div>
	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12">
				<table class="am-table am-table-striped am-table-hover table-main">
					<thead>
						<tr>
							<th></th>
							<th>提现单号</th>
							<th>用户(手机号码)</th>
							<th>提现金额</th>
							<th>提现卡号</th>
							<th>状态</th>
							<th>提现申请时间</th>
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
							<td>{{datalist.mobile}}</td>
							<td>￥{{datalist.cash}}</td>
							<td>{{datalist.cashcard}}</td>
							<td>{{datalist.state==0?'未处理':datalist.state==1?'处理中':datalist.state==2?'已处理':'未知状态'}}</td>
							<td>{{datalist.create_time}}</td>
							<td>
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button
											data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}"
											class="am-btn am-btn-default am-btn-xs am-text-secondary"
											ng-click="done(datalist.id)">
											<span class="am-icon-pencil-square-o"></span> 提现处理
										</button>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="am-cf">
					共{{count}} 条记录 当前第{{pageCurrent}}页
					<div class="am-fr">
						<ul class="am-pagination">
							<li><a ng-click='perView(pageCurrent)' href="#">«</a></li>
							<li ng-repeat="pages in pages" ng-class="pageClass(pages)"><a
								ng-click='byNum(pages.pageNum)' href="#">{{pages.pageNum}}</a></li>
							<li><a ng-click='next(pageCurrent)' href="#">»</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="am-modal am-modal-alert" tabindex="-1" id="doc-modal-1">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					提现处理 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
					<hr>
				</div>
				<form class="am-form am-form-horizontal">
					<div class="am-form-group am-form-group-sm">
						<div class="am-u-sm-10">
							<input type="hidden" class="am-form-field" id="id" name="id"
								ng-model="cashout.id" placeholder="标题">
						</div>
					</div>
					<div class="am-form-group am-form-group-sm">
						<label for="doc-ipt-3-1" class="am-u-sm-3 am-form-label">状态/state</label>
						<div class="am-u-sm-9">
							<select id="doc-select-1" ng-model="cashout.state">
								<option value="0" ng-selected="cashout.state==0">未处理</option>
								<option value="1" ng-selected="cashout.state==1">处理中</option>
								<option value="2" ng-selected="cashout.state==2">已处理</option>
							</select>
						</div>
					</div>
					<div class="am-form-group am-form-group-sm">
						<label for="doc-ipt-3-1" class="am-u-sm-3 am-form-label">凭证编号</label>
						<div class="am-u-sm-9">
							<input type="text" class="am-form-field" id="voucher"
								name="voucher" ng-model="cashout.voucher" placeholder="凭证编号">
						</div>
					</div>
					<div class="am-form-group am-form-group-sm">
						<label for="doc-ipt-3-1" class="am-u-sm-3 am-form-label">提现说明</label>
						<div class="am-u-sm-9">
							<input type="text" class="am-form-field" id="result"
								name="result" ng-model="cashout.result" placeholder="提现说明">
						</div>
					</div>
					<div class="am-form-group am-form-group-sm">
						<div class="am-u-sm-12">
							<button type="button" class="am-btn am-btn-primary"
								ng-click="submitCashOut()" ng-hide='subButton'>提交</button>
							<code>{{message}}</code>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</span>