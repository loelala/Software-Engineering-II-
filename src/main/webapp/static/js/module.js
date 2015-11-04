/**
 * @author Alexandra Vogel
 */
angular.module('SupplierManagement', [
    'ngRoute',
    'ui.router',
    'ngResource',
    'ngCookies',
    'smart-table',

    /* ---- custom modules ----*/
    // module for all suppliers (controller)
    'supplierList',
    // module for a specific supplier (controller)
    'supplierDetails',
    // module for connect to the backend (service)
    'supplierservice',
    // module for authentication (service)
    'authenticationservice',
    // module for user (service)
    'userservice',
    // module for login (controller)
    'login'


]);

// Call our app! important lines!!!
angular.module('SupplierManagement')
    .config(config)
    .run(run);


    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        // HOME STATES
        .state('home', {
            url: '/home',
            templateUrl: 'static/partials/home.html',
            resolve: {
                auth: ['$q', 'authenticationservice', function($q, authenticationservice) {
                    var userInfo = authenticationservice.getUserInfo();

                    if (userInfo) {
                        return $q.when(userInfo);
                    } else {
                        return $q.reject({ authenticated: false });
                    }
                }]
            }
        })

        .state('allSuppliers', {
            url: '/allSuppliers',
            templateUrl: 'static/partials/supplierList.html',
            controller: 'SupplierListCtrl',
            controllerAs: 'vm'
        })

        .state('supplier', {
            url: '/supplier',
            templateUrl: 'static/partials/supplier.html',
            controller: 'SupplierDetailsCtrl',
            controllerAs: 'vm'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'static/partials/loginPage.html',
            controller: 'LoginController',
            controllerAs: 'vm'
        })
    }

    run.$inject = ['$state', '$rootScope', '$location', '$cookieStore', '$http'];
    function run($state, $routeScope, $location, $cookieStore, $resource  ) {
        $state.transitionTo('home');

        $routeScope.$on('$routeChangeSuccess', function(userInfo) {
            console.log(userInfo);
        });

        $rootScope.$on('$rootChangeError', function(event, current, previous, eventObj) {
           if (eventObj.authenticated == false) {
               $state.transitionTo('login');
           }
        })

    }

