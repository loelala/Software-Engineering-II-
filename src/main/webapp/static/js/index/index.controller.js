/**
 * Controller for a index page.
 */
(function() {
    'use strict';

    angular.module('index', [])
        .controller('IndexCtrl', IndexCtrl);

    IndexCtrl.$inject = ['$state', 'shareService'];

    function IndexCtrl  ($state, shareService) {
        var vm = this;

        vm.isAdmin = shareService.isAdmin;

        vm.logout = function(){
            console.log("user logging out!")
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('isAdmin');
            vm.message = 'You are successfully logged out!'
        }
    }
})();