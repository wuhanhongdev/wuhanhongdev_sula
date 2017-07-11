var app = angular.module('myApp', ['ui.router','ng.ueditor']);
app.controller('myCtrl', function($scope, $http,$window ) {
	$scope.index_1 = false;
	$scope.index_2 = false;
	$scope.index_3 = false;
	$scope.index_4 = false;
	$scope.index_5 = false;
	$scope.index_6 = false;
	$scope.index_7 = false;
	$scope.index_8 = false;
	$scope.index_9 = false;
	$scope.index_10 = false;
	$scope.index_11 = false;
	$scope.selectM = function(index) {
		var nowR = $scope['index_'+index];
		if(nowR) {
			$scope['index_'+index]=false;
		} else {
			$scope['index_'+index]=true;
		}
	};
	
      $scope.num1 = false;  
      $scope.num2 = false;
      $scope.num3 = false;  
      $scope.num4 = false;  
      $scope.num5 = false;  
      $scope.num6 = false;  
      $scope.num7 = false;  
      $scope.num8 = false;  
      $scope.num9 = false;  
      $scope.num10 = false;  
      $scope.num11 = false;  
      $scope.num12 = false;  
      $scope.num13 = false;  
      $scope.num14 = false;  
      $scope.num15 = false;  
      $scope.num16 = false;  
      $scope.num17 = false; 
      $scope.num18 = false; 
      $scope.num19 = false; 
      $scope.num20 = false; 
      $scope.num21 = false; 
      $scope.num22 = false; 
      $scope.num23 = false; 
      $scope.num24 = false; 
      $scope.num25 = false; 
	$scope.selectedA = function(i){
		 $scope['num'+i] = true;
		 for(var k = 1 ; k <=25; k++){
			 if(i == k){
			 }else{
				 $scope['num'+k] = false;
			 }
		 }
	};
});
app.config(['$stateProvider','$urlRouterProvider', function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.when("", "/view");
	$stateProvider
	.state('view', {
        url:'/view',
        views:{
            'front':{
            	templateUrl:'publicPage/frontPage.jsp'
            },
            'footer':{
            	templateUrl:'publicPage/footerPage.jsp'
            }
        }
    })
    .state('owner', {
        url: '/owner',  
    	views:{
            'front':{
            	templateUrl:'auditing/owner_audit.jsp'  
            },
            'footer':{
            	templateUrl:'publicPage/footerPage.jsp'
            }
        }
    })
    .state('source', {
        url: '/source',  
    	views:{
            'front':{
            	templateUrl:'auditing/source_audit.jsp'  
            },
            'footer':{
            	templateUrl:'publicPage/footerPage.jsp'
            }
        }
    })
    .state('adv', {
        url: '/adv',  
    	views:{
            'front':{
            	templateUrl:'subinfo/adv_info.jsp'  
            },
            'footer':{
            	templateUrl:'publicPage/footerPage.jsp'
            }
        }
    })
    .state('consult',{
    	url:'/consult',
    	views:{
    		  'front':{
              	templateUrl:'subinfo/consult_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('assist',{
    	url:'/assist',
    	views:{
    		  'front':{
              	templateUrl:'subinfo/assist_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('goods',{
    	url:'/goods',
    	views:{
    		  'front':{
              	templateUrl:'complain/goods_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('trucks',{
    	url:'/trucks',
    	views:{
    		  'front':{
              	templateUrl:'complain/trucks_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('waybill',{
    	url:'/waybill',
    	views:{
    		  'front':{
              	templateUrl:'waybill/waybill_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    }).state('cashout',{
    	url:'/cashout',
    	views:{
    		  'front':{
              	templateUrl:'cashout/cash_out.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('cartop',{
    	url:'/cartop',
    	views:{
    		  'front':{
              	templateUrl:'information/car_top.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('goodstop',{
    	url:'/goodstop',
    	views:{
    		  'front':{
              	templateUrl:'information/goods_top.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('irule',{
    	url:'/irule',
    	views:{
    		  'front':{
              	templateUrl:'creditscore/int_rule.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('creditdeal',{
    	url:'/creditdeal',
    	views:{
    		  'front':{
              	templateUrl:'creditscore/credit_deal.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('products',{
    	url:'/products',
    	views:{
    		  'front':{
              	templateUrl:'products/product_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('orders',{
    	url:'/orders',
    	views:{
    		  'front':{
              	templateUrl:'products/order_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
     .state('refund',{
    	url:'/refund',
    	views:{
    		  'front':{
              	templateUrl:'products/order_refund.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('carowners',{
    	url:'/carowners',
    	views:{
    		  'front':{
              	templateUrl:'carowners/user_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('cars',{
    	url:'/cars',
    	views:{
    		  'front':{
              	templateUrl:'carowners/cars_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('sr',{
    	url:'/sr',
    	views:{
    		  'front':{
              	templateUrl:'goodsManager/ogoods_income.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('hz',{
    	url:'/hz',
    	views:{
    		  'front':{
              	templateUrl:'goodsManager/ogoods_info.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('fd',{
    	url:'/fd',
    	views:{
    		  'front':{
              	templateUrl:'goodsManager/ogoods_order.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('hrpp',{
    	url:'/hrpp',
    	views:{
    		  'front':{
              	templateUrl:'goodsManager/ogoods_pp.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    })
    .state('sdpp',{
    	url:'/sdpp',
    	views:{
    		  'front':{
              	templateUrl:'goodsManager/ogoods_sd.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    }).state('cas',{
    	url:'/cas',
    	views:{
    		  'front':{
              	templateUrl:'truckManager/truck_list.jsp'  
              },
              'footer':{
              	templateUrl:'publicPage/footerPage.jsp'
              }
    	}
    });
}]);

