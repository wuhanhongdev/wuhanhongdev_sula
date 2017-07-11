<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
#edui1_iframeholder {
	height: 370px
}
</style>
<span ng-controller="consultController">
	<div class="sula-right-title">
		<strong class="am-text-primary title-text"><span
			class="am-icon-newspaper-o"></span> 资讯管理</strong> / <small>管理员添加资讯信息，在APP做推广用途</small>
	</div>
	<div class="sula-right-content">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-12">
				<div class="am-btn-toolbar">
					<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-success"
							data-am-modal="{target: '#doc-modal-1',  width: 1000, height: 820}"
							ng-click="addAdvert()">
							<span class="am-icon-plus"></span> 添加资讯
						</button>
						</div>
						<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-warning"
							ng-click="updateAdvert()">
							<span class="am-icon-reply"></span> 修改资讯
						</button>
						</div>
						<div class="am-btn-group">
						<button type="button" class="am-btn am-btn-danger"
							ng-click="delAdvert()">
							<span class="am-icon-trash-o"></span> 删除资讯
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
							<th class="table-title">标题</th>
							<th class="table-type">作者</th>
							<th class="table-author am-hide-sm-only">创建时间</th>
							<th class="table-set">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-show="showTr">
							<td colspan="8" align="center" scope="row"
								class="null_information">※没有找到相关数据!</td>
						</tr>
						<tr ng-repeat="datalist in datalist">
							<td><input type="checkbox" ng-checked="select_one"
								ng-click="selectOne(datalist.id)" /></td>
							<td>{{datalist.id}}</td>
							<td>{{datalist.title}}</td>
							<td>{{datalist.author}}</td>
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
				详细信息 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<form class="am-form am-form-horizontal">


				<div class="am-form-group am-form-group-sm">
					<div class="am-u-sm-10">
						<input type="hidden" class="am-form-field" id="id" name="id"
							ng-model="advert.id" placeholder="标题">
					</div>
				</div>

				<div class="am-form-group am-form-group-sm">

					<label for="doc-ipt-3-1" class="am-u-sm-2 am-form-label">标题/title</label>
					<div class="am-u-sm-10">
						<input type="text" class="am-form-field" id="title" name="title"
							ng-model="advert.title" placeholder="标题">
					</div>
				</div>
				<div class="am-form-group am-form-group-sm">
					<label for="doc-ipt-3-1" class="am-u-sm-2 am-form-label">作者/author</label>
					<div class="am-u-sm-10">
						<input type="text" class="am-form-field" id="author" name="author"
							ng-model="advert.author" placeholder="作者">
					</div>
				</div>


				<div class="am-form-group am-form-group-sm">
					<label for="doc-ipt-3-1" class="am-u-sm-2 am-form-label">内容/cont</label>
					<div class="am-u-sm-10">
						<div class="ueditor" ready="ready" id="content" name="content"
							style="width:100%;height:100%" ng-model="advert.content"></div>
					</div>
				</div>

				<div class="am-form-group am-form-group-sm">
					<div class="am-u-sm-12">
						<button type="button" class="am-btn am-btn-primary"
							ng-click="submitAdvert()" ng-hide='subButton'>提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</span>