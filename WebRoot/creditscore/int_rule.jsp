<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="intRuleController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-book"></span> 积分规则</strong> / <small>管理远设置积分规则，以管控APP用户的行为</small>
	</div>

	<div class="sula-right-content">
		<div class="widget am-cf widget-body-lg am-margin-top-xl">
			<form class="am-form am-form-horizontal" ng-init="initData()">
				<input type="hidden" id="id" name="id" ng-model="data.id" />
				<div class="am-form-group">
					<label for="user-name" class="am-u-sm-3 am-form-label">基础分值
					</label>
					<div class="am-u-sm-6">
						<input type="text" id="warning_score" name="warning_score"
							ng-model="data.base_score" placeholder="基础分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">警戒分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="base_score" name="base_score"
							ng-model="data.warning_score" placeholder="警戒分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">信息发布分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="base_score" name="base_score"
							ng-model="data.publish_sore" placeholder="信息发布分值"> <small></small>
					</div>
					<div class="am-u-sm-3">
						<code>*</code>
					</div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">接单最低分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="order_sore" name="order_sore"
							ng-model="data.order_sore" placeholder="接单最低分值"> <small></small>
					</div>
					<div class="am-u-sm-3">
						<code>*</code>
					</div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">好评分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="bonus_score" name="bonus_score"
							ng-model="data.bonus_score" placeholder="好评分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">中评分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="mid_score" name="mid_score"
							ng-model="data.mid_score" placeholder="中评分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">差评分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="diff_score" name="diff_score"
							ng-model="data.diff_score" placeholder="差评分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>

				<div class="am-form-group">
					<label for="user-email" class="am-u-sm-3 am-form-label">投诉分值</label>
					<div class="am-u-sm-6">
						<input type="text" id="comp_score" name="comp_score"
							ng-model="data.comp_score" placeholder="差评分值"> <small></small>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-form-group">
					<div class="am-u-sm-9 am-u-sm-push-3">
						<button type="button" class="am-btn am-btn-primary"
							ng-click="modifyObj()">保存修改</button>
					</div>
				</div>
			</form>
			<div class="am-alert" data-am-alert ng-hide='alertDiv'>
				<button type="button" class="am-close">&times;</button>
				<p>{{message}}</p>
			</div>
		</div>
	</div>
</span>