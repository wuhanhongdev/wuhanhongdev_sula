app.controller('goodstopController', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	// 获取分页数据
	var _get = function(page) {
		$http({
			method : 'POST',
			url : 'inform/getGoodsInfoList',
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
				// 数据集
				var result = returnData.result;
				if(result.list == null  || result.list.length==0 ){
					$scope.showTr = true;
				}else{
					$scope.showTr = false;
				}
				// 设置数据列表
				$scope.datalist = result.list;
				// 设置总行数信息
				$scope.count = result.totalRow;
				// 设置总页数
				$scope.totalPage = result.totalPage; // 总页数
				// 声明 分页模块
				var paginate = new Array();
				// 公共方法 ， 获取分页 模块 数据 ，需要传入 当前页数 和 总页数 js/my.js
				paginate = getPaginate(page,$scope.totalPage);
				//　将获取的 paginate对象设置到 scope中
				$scope.pages = paginate;
				// 设置当前页码
				$scope.pageCurrent = page;
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	// 首次加载
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
	// 设置当前页 样式
	$scope.pageClass = function(pages) {
		if(pages.pageNum==$scope.pageCurrent) {
			return "am-active";
		}
	};
	// 设置行样式
	$scope.rowClass = function(datalist){
		var checkState = datalist.is_top;
		if(checkState==0) {
			return "am-danger";
		} else if(checkState ==1) {
			return "am-success";
		}
	};
	
	//查看
	$scope.lookup = function(id){
		var options = {'width':1000,'height':600} ;
		_openDialog('#doc-modal-1',options);
		$http({
			method : 'POST',
			url : 'inform/getGoodsById',
			data : {'id':id},
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
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
//	$scope.id = -1 ;
	// 置顶或者取消置顶
	$scope.dispost = function(id,is_top){
		if(is_top == 1){
			is_top = 0 ;
		}else{
			is_top = 1;
		}
		$http({
			method : 'POST',
			url : 'inform/modifyIsTopGoods',
			data : {'id':id,'is_top':is_top},
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
			}else{
				alert("~~操作失败~~");
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
		
		
	};

	//关闭模态框
	var _closeDialog = function(m) {
		var $modal = $(m);
		$modal.modal('close');
	};
	//开启模态框
	var _openDialog = function(m,options) {
		var $modal = $(m);
		$modal.modal(options);
		$modal.modal('open');
	};
	
});
