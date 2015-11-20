/**
 * Controller for a index page.
 */
(function() {
    'use strict';

    angular.module('index', [])
        .controller('IndexCtrl', IndexCtrl);

    IndexCtrl.$inject = ['$scope','$state', 'loginInformationHolderService'];

    function IndexCtrl  ($scope,$state, loginInformationHolderService) {
        var vm = this;

        $scope.isAdmin = loginInformationHolderService.isAdmin();
        $scope.isLoggedIn = loginInformationHolderService.isLoggedIn();
        $scope.$watch(loginInformationHolderService.isAdmin,function(){
            $scope.isAdmin = loginInformationHolderService.isAdmin();
            //console.log("watcher!!!! func share" + loginInformationHolderService.isAdmin())
            //console.log("watcher!!!! share" + loginInformationHolderService.isAdmin);
            //console.log("watcher!!!! " + $scope.isAdmin);

        });
        $scope.$watch(loginInformationHolderService.isLoggedIn,function(){
            $scope.isLoggedIn = loginInformationHolderService.isLoggedIn();
            //console.log("watcher!!!! func share" + loginInformationHolderService.isLoggedIn())
            //console.log("watcher!!!! share" + loginInformationHolderService.isLoggedIn);
            //console.log("watcher!!!! " + $scope.isLoggedIn);

        });
        vm.logout = function(){
            console.log("user logging out!")
            //localStorage.removeItem('isLoggedIn');
            //localStorage.removeItem('isAdmin');
            loginInformationHolderService.updateIsAdmin(false);
            loginInformationHolderService.updateIsLoggedIn(false);
            vm.message = 'You are successfully logged out!'
        }
    }
})();