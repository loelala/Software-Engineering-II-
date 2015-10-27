/**
 * Created by Alex on 23.10.15.
 */
angular.module('SupplierManagement', [
    'ngRoute',
    'ngResource',
    'smart-table',

    /* ---- custom modules ----*/
    // module for all suppliers (controller)
    'supplierList',
    // module for a specific supplier
    'supplierDetails',
    // module for connect to the backend (service)
    'supplierservice'
]);




/*app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
 $routeProvider.
 when("/supplier", {
 templateUrl: "partials/supplier.html",
 controller: "OneSupplierCtrl"
 })
 otherwise({
 redirectTo: '/index'
 });
 }]);*/

