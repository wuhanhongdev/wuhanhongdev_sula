app.controller('proController', function($scope, $http) {
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;		// 总行数
	$scope.pageCurrent = 1;	// 当前页数
	$scope.totalPage = 1;	// 总页数
	
	var _get = function(page) {
		$http({
			method : 'POST',
			url : 'product/getProductList',
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
		var checkState = datalist.is_show;
		if(checkState==0) {
			return "am-danger";
		} else if(checkState==1) {
			return "am-success";
		}
	};
	

	
	$scope.accid = 0 ;
	$scope.selectOne = function(id){
		$scope.accid = id ;
	};

	var _getPro = function(id){
		$http({
			method : 'POST',
			url : 'product/getOneProductById',
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
		$scope.imgDiv= false;
		$scope.proDiv = true;
		$scope.subButton = true;
		_getPro(id);
	};
	
	//关闭模态框
	var _closeDialog = function() {
		var $modal = $('#doc-modal-1');
		$modal.modal('close');
	};
	//开启模态框
	var _openDialog = function() {
		var $modal = $('#doc-modal-1');
		var options = {'width':800,'height':620} ;
		$modal.modal(options);
		$modal.modal('open');
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
				_clearForm();
				_get($scope.pageCurrent);
			}else{
				alert("~~操作失败~~");
			}
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	var _clearForm = function(){
		$scope.data = "";
	};
	//
	$scope.addpro = function(){
		$scope.imgDiv= true;
		$scope.proDiv = false;
		_clearForm();
		$scope.subButton = false;
		_openDialog();
	};
	//
	$scope.updatepro = function(){
		$scope.imgDiv= false;
		$scope.proDiv = false;
		if($scope.accid == 0){
			alert("~~请选择一条数据~~");
		}else{
		$scope.subButton = false;
		_openDialog();
		_getPro($scope.accid);
		}
	};
	$scope.modifyObj = function(){
		var fd = new FormData();
        var file = document.querySelector('input[type=file]').files[0];
        fd.append('img', file); 
		/**
		 * 组织对象
		 */
        var data = $scope.data;
        fd.append('id',data.id);
        fd.append('product',data.product);
        fd.append('points',data.points);
        fd.append('bewrite',data.bewrite);
        fd.append('nums',data.nums);
		if(data.id == undefined){
			_save(fd, 'product/addProduct');
		}else{
			_save(fd, 'product/modifyProductById');
		}
		$scope.accid = 0 ;
	};
	//删除
	$scope.delpro = function(){
		if($scope.accid == 0){
			alert("~~请选择一条数据~~");
		}else{
			//删除
			$http({
				method : 'POST',
				url : 'product/delProductById',
				data : {'id': $scope.accid},
				headers: {  
			        'Content-Type': 'application/x-www-form-urlencoded'  
			    },  
			    transformRequest: function (data) {  
			        return $.param(data);  
			    }  
			}).then(function successCallback(response) {
				var returnData = response.data;
				if(returnData.code == 1){
					_get($scope.pageCurrent);
				}else{
					alert("~~操作失败~~");
				}
			}, function errorCallback(response) {
				// 请求失败执行代码
				
			});
		}
	};
	//上下架
	$scope.doit = function(id){
		$http({
			method : 'POST',
			url : 'product/updateProductById',
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
				_get($scope.pageCurrent);
			}else{
				alert("~~操作失败~~");
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
});
