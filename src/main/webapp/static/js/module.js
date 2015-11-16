/**
 * @author Alexandra Vogel
 */
angular.module('WeDoIt', [
    'ngRoute',
    'ui.router',
    'ngResource',
    'ngCookies',
    'smart-table',
    'chart.js',
    'ngAnimate',
    'toastr',
    /* ---- custom modules ----*/
    'DataShareServiceModule',

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
    'index',
    'BarChartModule',
    'dateRange.directive'

]);

// Call our app! important lines!!!
angular.module('WeDoIt')
    .config(config)
    .run(run);


    config.$inject = ['$locationProvider','$stateProvider', '$urlRouterProvider','toastrConfig'];
    function config($locationProvider,$stateProvider, $urlRouterProvider,toastrConfig) {

        angular.extend(toastrConfig, {
            autoDismiss: true,
            containerId: 'toast-container',
            maxOpened: 2,
            newestOnTop: true,
            positionClass: 'toast-top-center',
            preventDuplicates: false,
            preventOpenDuplicates: true,
            target: 'body'
        });

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
        .state('comparison',
        {
            url:'/comparison',
            templateUrl: 'static/partials/supplierChartComparison.html',
            controller: 'BarChartController',
            controllerAs: 'vm'
        });
        $urlRouterProvider.otherwise("/login");

        // needed for our urls -- no # in front of our states
        //$locationProvider.html5Mode(true);

    }

    run.$inject = ['$state', '$rootScope', '$location', '$cookieStore', '$http'];

    function run($state, $rootScope) {

        $state.transitionTo('login');

        $rootScope.$on("$stateChangeStart",function(event, toState, toParams, fromState, fromParams){

            if(toState.name == "login")
            {
                console.log("$stateChangeStart - no login chainning! returning now")
                return
            }
            else
            {
                if(localStorage.isLoggedIn != 'true')
                {
                    console.log("tostate name " + toState.name)
                    console.log("stateChangeSTart - IF");
                    event.preventDefault();
                    $state.go('login');
                }
                else
                {
                    console.log("The User is Logged in? " + localStorage.isLoggedIn);
                }
            }

        });
        //window.onload = function() {
        //    if(localStorage.isLoggedIn != 'true') {
        //        $state.transitionTo('login');
        //    }
        //};

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

