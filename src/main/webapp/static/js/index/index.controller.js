/**
 * Controller for a index page.
 */
(function() {
    'use strict';

    angular.module('index', [])
        .controller('IndexCtrl', IndexCtrl);

    IndexCtrl.$inject = ['$state', 'AuthenticationService'];

    function IndexCtrl  ($state) {
        var vm = this;

        vm.logout = function(){
            console.log("user logging out!")
            localStorage.removeItem('isLoggedIn');
        }
    }
})();