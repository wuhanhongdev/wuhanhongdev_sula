<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<span ng-controller="ownerAudit">
  <div class="sula-right-title">
      <strong class="am-text-primary title-text"><span class="am-icon-truck"></span> 车主审核</strong> /审核APP用户提交的成为车主的申请
  </div>

  <div class="sula-right-content">
        <div class="am-g">
          <div class="am-u-sm-12 am-u-md-12 am-margin-bottom">
            <div class="am-btn-toolbar">
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-sula" ng-click="auditpass()">
                    <span class="am-icon-archive"></span> 审核通过
                  </button>
                </div>
                <div class="am-btn-group">
                  <button type="button" class="am-btn am-btn-romantic" ng-click="auditrebut()">
                    <span class="am-icon-exclamation-triangle"></span> 审核驳回
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
                  <th class="table-title">用户</th>
                  <th class="table-type">手机</th>
                  <th class="table-author am-hide-sm-only">状态</th>
                  <th class="table-date am-hide-sm-only">提交日期</th>
                  <th class="table-date am-hide-sm-only">审核日期</th>
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
                  <td>{{datalist.finish_time}}</td>
                  <td>
                    <div class="am-btn-toolbar">
                      <div class="am-btn-group am-btn-group-xs">
                        <button
                          data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 400, height: 225}"
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
      <div class="am-modal-hd">
        <h2>详细信息</h2> 
        <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
      </div>

      <div class="am-modal-bd">

        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-text-left"><strong style="width:200px; display:inline-block" class="am-text-right">昵称：</strong>{{nick_name}}</div>
            <div class="am-u-sm-6 am-text-left"><strong>手机：</strong>{{mobile_}}</div>
        </div>

        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-text-left"><strong style="width:200px; display:inline-block" class="am-text-right">身份证照片：</strong><img ng-src="{{img_img1}}" width="150"; height="80" alt=""/></div>
            <div class="am-u-sm-6 am-text-left"><strong>驾驶证：</strong><img ng-src="{{img_img3}}" width="150"; height="80" alt=""/></div>
        </div>
        
      </div>
    </div>
</div>
  
  
</span>