angular.module('SupplierManagement', [
    'ngRoute',
    'SupplierManagement.controllers'
]);

/*.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
	when("/index", {templateUrl: "partials/index.html", controller: "IndexCtrl"}).
	when("/suppliers", {templateUrl: "partials/supplierList.html", controller: "SupplierCtrl"}).
	otherwise({redirectTo: '/index'});
}]);*/