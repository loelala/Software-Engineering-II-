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
    'ui.bootstrap',
    'toastr',
    /* ---- custom modules ----*/
    'DataShareServiceModule',
    // module for all suppliers (controller)
    //'supplier',
    //'supplierList',
    'supplierListByDate',
    // module for a specific supplier (controller)
    //'supplierDetails',
    // module for connect to the backend (service)
    'supplierservice',
    'supplierserviceByDate',
    // module for authentication (service)
    'authenticationservice',
    // module for user (service)
    'userservice',
    // module for login (controller)
    'login',
    'index',
    'newuser',
    'barChartModule',
    'dateRange.directive',
    'loginInformationHolderModule'

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
        .state('supplier', {
            url: '/allSuppliers/byDate',
            templateUrl: 'static/partials/supplierListByDate.html',
            controller: 'SupplierListByDateCtrl',
            controllerAs: 'vm'
        })
        .state('useradmin', {
            url: '/useradmin',
            templateUrl: 'static/partials/userAdminPartial.html',
            controller: 'UserController',
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

    run.$inject = ['$state', '$rootScope', '$location', '$cookieStore', '$http','loginInformationHolderService'];

    function run($state, $rootScope,$location,$cookieStore,$http,loginInformationHolderService) {

        $state.transitionTo('login');

        $rootScope.$on("$stateChangeStart",function(event, toState, toParams, fromState, fromParams){

            if(toState.name == "login")
            {
                console.log("$stateChangeStart - no login chainning! returning now");
                return
            }
            else
            {
                console.log("is logged in status from loginInformationHolerService" + loginInformationHolderService.isLoggedIn());
                if(!loginInformationHolderService.isLoggedIn())
                {
                    console.log("tostate name " + toState.name)
                    console.log("stateChangeSTart - IF");
                    event.preventDefault();
                    $state.go('login');
                }
                //else
                //{
                //    //console.log("The User is Logged in? " + localStorage.isLoggedIn);
                //}
            }

        });
    }

