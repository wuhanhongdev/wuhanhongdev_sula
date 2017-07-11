app.controller('ordController', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	var _get = function(page) {
		$http({
			method : 'POST',
			url : 'order/getOrderList',
			data : {'page':page},
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
		if(checkState==1) {
			return "am-danger";
		} else if(checkState==2) {
			return "am-primary";
		}else if(checkState == 3){
			return "am-warning";
		}else{
			return "am-success";
		}
	};
	

	

	var _getOrder = function(id){
		$http({
			method : 'POST',
			url : 'order/getOrderInfoById',
			data : {'id': id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			//alert(returnData.result.product);
			
			if(returnData.code == 1){
				$scope.data = returnData.result ;
			}else{
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	//
	$scope.lookup = function(id){
	
		var options = {'width':960,'height':520} ;
		_openDialog('#doc-modal-1',options);
		_getOrder(id);
	};
	/**
	 * 物流公司
	 */
	$scope.exid = 0;
	$scope.doit = function(id,type){
		if(type ==2){
		
		var options = {'width':560,'height':320} ;
		_openDialog('#doc-modal-2',options);
		$scope.exid = id;
		}else{
			alert('~~禁止操作~~');
		}
	};
	
	$scope.modifyObj = function(){
		$http({
			method : 'POST',
			url : 'order/sendOrder',
			data : {'id': $scope.exid,'exp':$scope.op.exp,'expno':$scope.op.expno},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			// 请求成功执行代码
			_closeDialog('#doc-modal-2');
			_get($scope.pageCurrent);
			$scope.exid = 0;
		}, function errorCallback(response) {
			// 请求失败执行代码
			$scope.message = "~~未知错误~~";
	});
};
	
	//关闭模态框
	var _closeDialog = function(modal) {
		var $modal = $(modal);
		$modal.modal('close');
	};
	//开启模态框
	var _openDialog = function(modal,options) {
		var $modal = $(modal);
		$modal.modal(options);
		$modal.modal('open');
	};
	
	//写入数据库操作

	var _clearForm = function(){
		$scope.data = "";
	};
	//

});
