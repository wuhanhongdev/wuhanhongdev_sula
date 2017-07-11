app.controller('intRuleController', function($scope, $http) {
	$scope.alertDiv = true;
	$scope.initData = function(){
		$http({
			method: 'POST',
			url: 'credit/getCreditObject'
		}).then(function successCallback(response) {
				// 请求成功执行代码
				var data = response.data;
				if(data.code == 1){
					$scope.data = data.result;
				}else{
					$scope.message = "";
				}
			}, function errorCallback(response) {
				// 请求失败执行代码
				$scope.message = "~~未知错误~~";
		});
	};
	//提交
	$scope.modifyObj = function(){
		var data = $scope.data;
		$http({
			method: 'POST',
			url: 'credit/modifyCreditObject',
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
				$scope.message = response.data.message;
				$scope.alertDiv = false;
			}else{
				$scope.message = response.data.message;
				$scope.alertDiv = false;
			};
			
		}, function errorCallback(response) {
			// 请求失败执行代码
			
		});
		
	};
	
});