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
    'login',
    'index'


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
            templateUrl: 'static/partials/home.html'
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

    function run($state) {

        $state.transitionTo('login');

        window.onload = function() {
            if(localStorage.isLoggedIn != 'true') {
                $state.transitionTo('login');
            }
        };

        /*
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authData; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $state.transitionTo('login');
            }
        });

        $rootScope.$on('$rootChangeError', function(event, current, previous, eventObj) {
            $state.transitionTo('login');
        });

        */

    }

