app.controller('ownerAudit', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	var _get = function(page) {
		$http({
			method : 'POST',
			url : 'auditing/auditOwnerList',
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
		if(checkState==3) {
			return "am-danger";
		} else if(checkState==2) {
			return "am-success";
		}
	};
	
	//审核
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
				alert("~~操作成功~~");
				$scope.accid = 0 ;
				_get($scope.pageCurrent);
			}else{
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	
	};
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
				alert("~~操作成功~~");
				$scope.accid = 0 ;
				_get($scope.pageCurrent);
			}else{
				alert("~~操作失败~~");
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
			alert("~~请选择一条数据~~");
		}else{
			//alert($scope.accid);
			_checkpass($scope.accid);
		}
	};
	$scope.auditrebut = function(){
		if($scope.accid == 0){
			alert("~~请选择一条数据~~");
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
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
});
