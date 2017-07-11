app.controller('advertController', function($scope, $http) {
	
	//配置
	$scope.pageNum = 0;		// 页数
	
	//变量
	$scope.count = 1;
	$scope.pageCurrent = 1;	// 当前页数
	
	
	var _get = function(page) {
		$http({
			method : 'POST',
			url : 'subinfo/getAdvertList',
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
				
				var totalPage = result.totalPage; // 总页数
				
				// 公共方法 ， 获取分页 列表 ，需要传入 当前页数 和 总页数 js/my.js
				paginate = getPaginate(page,totalPage);
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
		page = page+1>$scope.count?$scope.count:page+1;
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
	//审核
//	var _checkpass = function(id){};
//	var _checkrebut = function(id){};
	
	$scope.accid = 0 ;
	$scope.selectOne = function(id){
		$scope.accid = id ;
	};

	//查看
	$scope.lookup = function(id){
		$scope.subButton = true;
		_openDialog();
		_getAdvert(id);
	};
	//新增
	$scope.addAdvert = function(){
		$scope.subButton = false;
	};
	//修改
	$scope.updateAdvert = function(){
		$scope.subButton = false;
		if( $scope.accid == 0 ){
			alert('~~请选择一条数据~~');
		}else{
			_openDialog();
			_getAdvert($scope.accid);
		}
	};
	//删除
	$scope.delAdvert = function(){
		if($scope.accid ==0){
			alert('~~请选择一条数据~~');
		}else{
			$http({
				method : 'POST',
				url : 'subinfo/delAdvert',
				data : {'id':$scope.accid },
				headers: {  
			        'Content-Type': 'application/x-www-form-urlencoded'  
			    },  
			    transformRequest: function (data) {  
			        return $.param(data);  
			    }  
			}).then(function successCallback(response) {
				var returnData = response.data;
				if(returnData.code == 1){
					_get($scope.pageCurrent );
					$scope.accid ==0;
				}else{
					alert("~~操作失败~~");
				};
				
			}, function errorCallback(response) {
				// 请求失败执行代码
				
			});
		}
	};
	//保存动作
	var _save = function(data,url){
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
				_get($scope.pageCurrent );
				$scope.accid = 0 ;
				_clearForm();
				_closeDialog();
			}else{
				alert("~~操作失败~~");
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	//获取对象
	var _getAdvert = function(id){
		$http({
			method : 'POST',
			url : 'subinfo/getAdvertByID',
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
				$scope.advert = returnData.result;
			}else{
				alert("~~操作失败~~");
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
	};
	//关闭模态框
	var _closeDialog = function() {
		var $modal = $('#doc-modal-1');
		$modal.modal('close');
	};
	//开启模态框
	var _openDialog = function() {
		var $modal = $('#doc-modal-1');
		var options = {'width':1000,'height':820} ;
		$modal.modal(options);
		$modal.modal('open');
	};
	// 清空表单
	var _clearForm = function(){
		$scope.advert = "";
	};
	//提交动作
	$scope.submitAdvert = function(){
		var data = $scope.advert ;
		if(data.id == null){
			_save(data,'subinfo/addAdvert');
		}else{
			_save(data,'subinfo/updateAdvert');
		}
	};
	
	
	
});
