/**
 * @author Alexandra Vogel
 */
angular.module('SupplierManagement', [
    'ngRoute',
    'ui.router',
    'ngResource',
    'smart-table',

    /* ---- custom modules ----*/
    // module for all suppliers (controller)
    'supplierList',
    // module for a specific supplier
    'supplierDetails',
    // module for connect to the backend (service)
    'supplierservice'
    // model for the index (controller)

    // model for connection to the backend (service)


]);


angular.module('SupplierManagement')
    .config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        // HOME STATES
        .state('home', {
            url: '/home',
            templateUrl: 'static/partials/home.html'
        })

        .state('allSuppliers', {
            url: '/allSuppliers',
            templateUrl: 'static/partials/supplierList.html',
            controller: 'SupplierListCtrl'
        })

        .state('supplier', {
            url: '/supplier',
            templateUrl: 'static/partials/supplier.html',
            controller: 'SupplierDetailsCtrl'
        });

});

angular.module('SupplierManagement').run(['$state', function ($state) {
    $state.transitionTo('home');
}]);

