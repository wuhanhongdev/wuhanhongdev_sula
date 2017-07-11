app.controller('userController', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	var _get = function(page,keyword) {
		$http({
			method : 'POST',
			url : 'cawn/getCarOwnerList',
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
		var checkState = datalist.state;
		if(checkState==0) {
			return "am-danger";
		} else if(checkState==1) {
			return "am-success";
		}
	};
	
//构造弹出框
	var _openDialog = function (){
		var $modal = $('#doc-modal-1');
		var options = {'width':800,'height':620} ;
		$modal.modal(options);
		$modal.modal('open');
	};
	var _closeDialog = function(){
		var $modal = $('#doc-modal-1');
		$modal.modal('close');
	};
	var _clearDialog = function(){
		$scope.data = "";
		$scope.selectid = 0 ;
	};

	//激活添加功能
	$scope.addUser = function(){
		$scope.imgDiv = true;   // 隐藏图片显示板块
		_openDialog();
	};

	//写入数据库操作
	var _save = function(data,url){
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers: {  
				'Content-Type':undefined,
		    },  
		    transformRequest: angular.identity
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code == 1){
				_closeDialog();
				_clearDialog();
				_get($scope.pageCurrent);
			}else{
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	
	
	//保存按钮
	$scope.modifyObj = function(){
		var fd = new FormData();
        var file = document.querySelector('input[type=file]').files[0];
        fd.append('img', file); 
		/**
		 * 组织对象
		 */
        var data = $scope.data;
        fd.append('id',data.id);
        fd.append('mobile',data.mobile);
        fd.append('nick',data.nick);
        fd.append('password',data.password);
     
        if(data.id == undefined){
        		_save(fd,'cawn/addCarOwner');
        }
        else{
        	  _save(fd,'cawn/updateCarOwnerById');
        }
	};
	//自定义函数 - 找到一个对象
	var _getObj = function(id){
		$http({
			method : 'POST',
			url : 'cawn/getCarOwnerById',
			data : {'id':id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code==1) {
				$scope.imgDiv = false;   // 显示图片显示板块
				$scope.data =returnData.result;
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	//查看
	$scope.lookup = function(id){
		_openDialog();
		_getObj(id);
	};
	
	$scope.selectid = 0 ;
	$scope.selectOne = function(id){
		$scope.selectid = id ;
	};
	
	$scope.updateUser = function(){
		if($scope.selectid == 0){
			alert("~~请选择一条要修改的数据~~");
		}else{
			_openDialog();
			_getObj($scope.selectid);
		}
	};
	
	var __openDialogCar = function(){
		var $modal = $('#doc-modal-2');
		var options = {'width':800,'height':620} ;
		$modal.modal(options);
		$modal.modal('open');
	};
	var _closeDialogCar = function(){
		var $modal = $('#doc-modal-2');
		$modal.modal('close');
	};
	//查看车辆
	$scope.gerUserCar = function(){
		if($scope.selectid == 0){
			alert("~~请选择要查看的车主~~");
		}else{
			__openDialogCar();
			$http({
				method : 'POST',
				url : 'cawn/getUserCars',
				data : {'id':$scope.selectid},
				headers: {  
			        'Content-Type': 'application/x-www-form-urlencoded'  
			    },  
			    transformRequest: function (data) {  
			        return $.param(data);  
			    }  
			}).then(function successCallback(response) {
				var returnData = response.data;
				if(returnData.code == 1) {
					$scope.cars =returnData.result;
					$scope.showTr = false;
				} else {
					$scope.showTr = true;
				}
			}, function errorCallback(response) {
				// 请求失败执行代码
				
			});
		}
	};

	
	
	var __openDialogWay = function(){
		var $modal = $('#doc-modal-3');
		var options = {'width':800,'height':620} ;
		$modal.modal(options);
		$modal.modal('open');
	};
	var _closeDialogWay = function(){
		var $modal = $('#doc-modal-3');
		$modal.modal('close');
	};
	//接单信息
	$scope.getWaybill = function(){
		if($scope.selectid == 0){
			alert("~~请选择要查看的车主~~");
		}else{
			__openDialogWay();
			_getW($scope.selectid);
		}
	};
	$scope.pn = 0;		// 页数
	
	//变量
	$scope.num = 1;		// 总行数
	$scope.pagec = 1;	// 当前页数
	$scope.total = 1;	// 总页数
	
	var _getW = function(page,id) {
		$http({
			method : 'POST',
			url : 'cawn/getWaysById',
			data : {'page':page,'id':id},
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
				
				$scope.ways = result.list;
				$scope.num = result.totalRow;
				
				var paginate = new Array();
				
				$scope.total = result.totalPage; // 总页数
				
				// 公共方法 ， 获取分页 列表 ，需要传入 当前页数 和 总页数 js/my.js
				paginate = getPaginate(page,$scope.total);
				//　将获取的 paginate对象设置到 scope中
				$scope.pn = paginate;
				$scope.pagec = page;
				
				$scope.selectid == 0;
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	
	
	
	var _openDialogPurse = function(){
		var $modal = $('#doc-modal-4');
		var options = {'width':800,'height':620} ;
		$modal.modal(options);
		$modal.modal('open');
	};
	var _closeDialogPurse  = function(){
		var $modal = $('#doc-modal-4');
		$modal.modal('close');
	};
	
	//收入信息
	
	$scope.gerPurseInfo = function(){
		if($scope.selectid == 0){
			alert("~~请选择要查看的车主~~");
		}else{
		_openDialogPurse();
		
		$http({
			method : 'POST',
			url : 'cawn/getPurseById',
			data : {'id':$scope.selectid},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code==1) {
				$scope.purse = returnData.result;
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
		}
	};
	//拉黑
	$scope.blackm = function(id){
		$http({
			method : 'POST',
			url : 'cawn/updateUserState',
			data : {'id':id},
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if(returnData.code==1) {
			_get($scope.pageCurrent);
			} else {
				
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});
	};
	
	//查询
	$scope.search = function(){
		_get(1,$scope.keyword);
		
	};
});
