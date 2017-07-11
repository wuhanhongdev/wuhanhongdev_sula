app.controller('carCon', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	// 获取分页数据
	var _get = function(page,keyword) {
		$http({
			method : 'POST',
			url : 'ccar/getCompanyCars',
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
	_get($scope.pageCurrent,$scope.keyword);
	// 查询
	$scope.search = function() {
		_get(1,$scope.keyword);
	};
	// 根据 页数 跳转到 目标 分页
	$scope.byNum = function(page) {
		_get(page,$scope.keyword);
	};
	$scope.next = function(page) {
		page = page+1>$scope.totalPage?1:page+1;
		_get(page,$scope.keyword);
	};
	$scope.perView = function(page) {
		page = page-1<=0?1:page-1;
		_get(page,$scope.keyword);
	};
	// 设置当前页 样式
	$scope.pageClass = function(pages) {
		if(pages.pageNum==$scope.pageCurrent) {
			return "am-active";
		}
	};
	// 设置行样式
	$scope.rowClass = function(datalist){
		var paystate = datalist.paystate;
		if(paystate==0) {
			return "am-danger";
		} else if(checkState==2) {
			return "am-success";
		}
	};
});