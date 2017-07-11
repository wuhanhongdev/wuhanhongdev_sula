app.controller('sdCons', function($scope, $http) {
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
				$scope.car = returnData.result;
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
	
	//公司车辆【派单】
	$scope.companyCar = function(){
		
		if($scope.id==0 || $scope.sj == ''){
			alert('~~请选择一调数据~~');
		}else{
			
//			_clearDialog();
			var options = {'width':1000,'height':800} ;
			_openDialog('#doc-modal-1',options);
			var url = 'shipper/getCompanyCars';
			var data = {'id':$scope.check_id,'mobile':$scope.sj};	
			_getObj(url,data);
			//_getListCar();
		}
		
	
	};
	
	//匹配 hyid 货源ID  tel 货主电话 mobile 车主电话 carid 车辆ID
	$scope.cdp = function(hyid,tel,mobile,carid){
		//alert(hyid+"--"+tel+"--"+mobile+"--"+carid);
		var url ='shipper/addInfoToWayBill';
		var data = {'hyid':hyid,'tel':tel,'carid':carid,'mobile':mobile};
		_save(url,data);
	};
	//社会车辆 手动匹配
	$scope.societyCar = function(){
		if($scope.id==0 || $scope.sj == ''){
			alert('~~请选择一调数据~~');
		}else{
			
//			_clearDialog();
			var options = {'width':600,'height':400} ;
			_openDialog('#doc-modal-2',options);
			
			var data = {'id':$scope.check_id,'mobile':$scope.sj};	
			$scope.temp =  data ;
			
			
//			var url = 'shipper/getCompanyCars';
//			
//			_getObj(url,data);
			//_getListCar();
		}
	};
	
	$scope.createion = function(){
		_save('shipper/addSocietyCarInfoToWayBill',$scope.temp);
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
				alert(returnData.message);
				_get($scope.pageCurrent);
				 _closeDialog('#doc-modal-1');
				 _closeDialog('#doc-modal-2');
			}else{
				alert("~~操作失败~~");
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			_get($scope.pageCurrent);
		});
	};
	
	

	
	/**
	 * 获取选择ID
	 */
	$scope.check_id = 0;
	$scope.sj = '';
	$scope.selectOne = function(id,mo){
		$scope.check_id = id;
		$scope.sj = mo;
	};
	
});
