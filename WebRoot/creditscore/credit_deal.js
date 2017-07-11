app.controller('creditDealController', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	// 获取分页数据
	var _get = function(page,creditState) {
		$http({
			method : 'POST',
			url : 'credit/creditUserList',
			data : {'page':page,'creditState':creditState},
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
				// 设置数据列表
				$scope.datalist = result.list;
				if(result.list == null  || result.list.length==0 ){
					$scope.showTr = true;
				}else{
					$scope.showTr = false;
				}
				
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
	_get($scope.pageCurrent,$scope.creditState);
	
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
		var checkState = datalist.iswarning;
		var publishState = datalist.ispublish;
		var orderState = datalist.isorder;
		if(checkState==1&&publishState!=0&&orderState!=0) {
			return "am-active";
		}
		if(publishState==0||orderState==0) {
			return "am-danger";
		}
	};
	
	$scope.change = function() {
		_get(1,$scope.creditState);
	};
	
	$scope.accid = 0 ;
	$scope.selectOne = function(id){
		$scope.accid = id ;
	};
	$scope.changeState = function(i) {
		if($scope.accid==0) {
			alert("~~~请选择信息~~~");
		} else {
			if(i==1) {
				relieveAccount(1);
			} else {
				relieveAccount(0);
			}
		}
	};
	// 帐号解禁
	var relieveAccount = function(state) {
		$http({
			method : 'POST',
			url : 'credit/setAccountState',
			data : {'id':$scope.accid,'state':state},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			// 数据集
			if(returnData.code==1) {
				alert("~~~操作成功~~~");
				_get(1,$scope.creditState);
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
});
