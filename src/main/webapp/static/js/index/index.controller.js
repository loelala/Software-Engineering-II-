/**
 * Controller for a index page.
 */
(function() {
    'use strict';

    angular.module('index', [])
        .controller('IndexCtrl', IndexCtrl);

    IndexCtrl.$inject = ['$scope', 'loginInformationHolderService'];

    function IndexCtrl  ($scope, loginInformationHolderService) {
        var vm = this;

        vm.actualUser = loginInformationHolderService.actualUser();

        $scope.isAdmin = loginInformationHolderService.isAdmin();
        $scope.isLoggedIn = loginInformationHolderService.isLoggedIn();
        $scope.$watch(loginInformationHolderService.isAdmin,function(){
            $scope.isAdmin = loginInformationHolderService.isAdmin();

        });
        $scope.$watch(loginInformationHolderService.isLoggedIn,function(){
            $scope.isLoggedIn = loginInformationHolderService.isLoggedIn();

        });

        $scope.$watch(loginInformationHolderService.actualUser , function() {
            vm.actualUser = loginInformationHolderService.actualUser();
        });

        vm.logout = function(){
            console.log("user logging out!");
            loginInformationHolderService.updateIsAdmin(false);
            loginInformationHolderService.updateIsLoggedIn(false);
            loginInformationHolderService.updateActualUser('');
            vm.message = 'You are successfully logged out!'
        }
    }
})();