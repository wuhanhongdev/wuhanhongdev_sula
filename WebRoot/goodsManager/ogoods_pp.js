app.controller('ppCon', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	var _get = function(page,keyword) {
		$http({
			method : 'POST',
			url : 'shipper/getWayBills',
			data : {'page':page,'keyword':keyword},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code==1) {
				
				//数据集
				var result = returnData.result;
				if(result.list == null  || result.list.length==0 ){
					$scope.showTr = true;
				}else{
					$scope.showTr = false;
				}
				
				$scope.datalist = result.list;
				$scope.count = result.totalRow;
				
				var paginate = new Array();
				
				$scope.totalPage = result.totalPage; // 总页数
				
				// 公共方法 ， 获取分页 列表 ，需要传入 当前页数 和 总页数 js/my.js
				paginate = getPaginate(page,$scope.totalPage);
				//　将获取的 paginate对象设置到 scope中
				$scope.pages = paginate;
				$scope.pageCurrent = page;
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	_get($scope.pageCurrent);
	
	// 根据 页数 跳转到 目标 分页
	$scope.byNum = function(page) {
		_get(page);
	};
	$scope.next = function(page) {
		page = page+1>$scope.totalPage?1:page+1;
		_get(page);
	};
	$scope.perView = function(page) {
		page = page-1<=0?1:page-1;
		_get(page);
	};
	$scope.pageClass = function(pages) {
		if(pages.pageNum==$scope.pageCurrent) {
			return "am-active";
		}
	};
	// 行样式
	$scope.rowClass = function(datalist){
		var checkState = datalist.type;
		if(checkState==4) {
			return "am-danger";
		} else if(checkState==5) {
			return "am-success";
		}else{
			return "am-primary";
		}
	};
	
	//查询
	$scope.search = function(){
		_get(1,$scope.keyword);
	};
	//-获取一个---
	var _getObj = function(url,data){
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				$scope.data = returnData.result;
			}else{
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	
	//-----
	/**
	 * 构造弹出框
	 * 打开
	 * 关闭
	 * 清空
	 */
	
	var _closeDialog = function(m) {
		var $modal = $(m);
		$modal.modal('close');
	};
	
	var _openDialog = function(m,options) {
		var $modal = $(m);
		$modal.modal(options);
		$modal.modal('open');
	};
	
	var _clearDialog = function(){
		$scope.data = "";
		$scope.check_id = 0;
	};
	
	//添加按钮激活
	$scope.add = function(){
		_clearDialog();
		var options = {'width':1000,'height':800} ;
		_openDialog('#doc-modal-1',options);
	};
	//修改按钮激活
	$scope.update = function(){
		if($scope.check_id == 0){
			alert('~~请选择你要修改的数据~~');
		}else{
			var url = 'shipper/getGoodsInfoById';
			var data = {'id':$scope.check_id};
			_getObj(url,data);
			var options = {'width':1000,'height':800} ;
			_openDialog('#doc-modal-1',options);
		}
	};
	
	//序列化方法
	var _save = function(url,data){
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				$scope.data = returnData.result;
				_get($scope.pageCurrent);
				 _closeDialog('#doc-modal-1');
				 _clearDialog();
			}else{
				alert("~~操作失败,请输入系统存在货主,否则无法生成货源~~");
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			_get($scope.pageCurrent);
		});
	};
	
	
	/**
	 * 保存修改激活方法
	 */
	$scope.modifyObj=function(){
		var data = $scope.data ;
		if(data.id == undefined){
			_save('shipper/addGoodInfo',data);
		}else{
			_save('shipper/modifyGoodInfo',data);
		}
	};
	
	/**
	 * 获取选择ID
	 */
	$scope.check_id = 0;
	$scope.selectOne = function(id){
		$scope.check_id = id;
	};
	
});
