var app = angular.module('myApp', []);
app.controller('myCtrl', function( $scope, $http, $window ) {
	$scope.alertDiv = true ;
	$scope.login = function() {
		$http({
			method : 'POST',
			url : 'login/adminlogin',
			data : $scope.formData,
			headers: {  
		        'Content-Type': 'application/x-www-form-urlencoded'  
		    },  
		    transformRequest: function (data) {  
		        return $.param(data);  
		    }  
		}).then(function successCallback(response) {
			var returnData = response.data;
			if (returnData.code == 1) {
				$window.location.href = "Framework.jsp";
				$scope.alertDiv = true ;
			} else {
				$scope.alertDiv = false;
				$scope.msgInfo = "用户名或密码错误!";
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});
	};
});