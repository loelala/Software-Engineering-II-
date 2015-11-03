/**
 * Controller for a index page.
 */
(function() {
    'use strict';

    angular.module('index', [])
        .controller('IndexCtrl', IndexCtrl);

    IndexCtrl.$inject = ['indexservice'];

    function IndexCtrl (indexservice) {
        var vm = this;

        vm.login = function() {

        }
    }
})();