app.controller('sourceAudit', function($scope, $http) {
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
			url : 'auditing/auditsourceList',
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
		var checkState = datalist.type;
		if(checkState==3) {
			return "am-danger";
		} else if(checkState==2) {
			return "am-success";
		}
	};
	
	//审核通过
	var _checkpass = function(id){
		$http({
			method : 'POST',
			url : 'auditing/auditPass',
			data : {'id': id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				$scope.alert_msg = "~~操作成功~~";
				$('#alert').modal('toggle');
				$scope.accid = 0 ;
				_get($scope.pageCurrent);
			}else{
				$scope.alert_msg = "~~操作失败~~";
				$('#alert').modal('toggle');
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	// 审核驳回
	var _checkrebut = function(id){
		$http({
			method : 'POST',
			url : 'auditing/auditRebut',
			data : {'id': id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				$scope.alert_msg = "~~操作成功~~";
				$('#alert').modal('toggle');
				$scope.accid = 0 ;
				_get($scope.pageCurrent);
			}else{
				$scope.alert_msg = "~~操作失败~~";
				$('#alert').modal('toggle');
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});
	};
	
	$scope.accid = 0 ;
	$scope.selectOne = function(id){
		$scope.accid = id ;
	};
	$scope.auditpass = function(){
		if($scope.accid == 0){
			$scope.alert_msg = "~~请选择一条数据~~";
			$('#alert').modal('toggle');
		}else{
			//alert($scope.accid);
			_checkpass($scope.accid);
		}
	};
	$scope.auditrebut = function(){
		if($scope.accid == 0){
			$scope.alert_msg = "~~请选择一条数据~~";
			$('#alert').modal('toggle');
		}else{
			//alert($scope.accid);
			_checkrebut($scope.accid);
		}
	};
	//
	$scope.lookup = function(id){
		$http({
			method : 'POST',
			url : 'auditing/getObjectById',
			data : {'id': id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				var temp = returnData.result;
				$scope.nick_name = temp.nick;
				$scope.mobile_ = temp.mobile;
				$scope.img_img1 = temp.img1;
				$scope.img_img2 = temp.img2;
			}else{
				$scope.alert_msg = "~~操作失败~~";
				$('#alert').modal('toggle');
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
});
