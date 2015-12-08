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
    'daterangepicker',
    /* ---- custom modules ----*/
    'DataShareServiceModule',
    'supplierListByDate',
    'supplierservice',
    'supplierserviceByDate',
    'authenticationservice',
    'userservice',
    'alluserservice',
    'login',
    'index',
    'newuser',
    'editUser',
    'deleteUser',
    'barChartModule',
    'datepicker.directive',
    'dateRange.directive',
    'loginInformationHolderModule'

]);

// Call our app! important lines!!!
angular.module('WeDoIt')
    .config(config)
    .run(run);


    config.$inject = ['$stateProvider', '$urlRouterProvider','toastrConfig'];
    function config($stateProvider, $urlRouterProvider, toastrConfig) {

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
        .state('settings', {
            url: '/settings',
            templateUrl: 'static/partials/settings.html',
            controller: 'SettingController',
            controllerAs: 'vm'
        })
        .state('comparison',
        {
            url:'/comparison',
            templateUrl: 'static/partials/supplierChartComparison.html',
            controller: 'barChartController',
            controllerAs: 'vm'
        });
        $urlRouterProvider.otherwise("/login");


    }

    run.$inject = ['$state', '$rootScope','loginInformationHolderService'];

    function run($state, $rootScope,loginInformationHolderService) {

        $state.transitionTo('login');

        $rootScope.$on("$stateChangeStart",function(event, toState){

            if(toState.name == "login")
            {
                console.log("$stateChangeStart - no login chainning! returning now");
            }
            else
            {
                console.log("is logged in status from loginInformationHolerService"
                    + loginInformationHolderService.isLoggedIn());

                if(!loginInformationHolderService.isLoggedIn())
                {
                    console.log("tostate name " + toState.name);
                    console.log("stateChangeSTart - IF");
                    event.preventDefault();
                    $state.go('login');
                }
            }

        });
    }

