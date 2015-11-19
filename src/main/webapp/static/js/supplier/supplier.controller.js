(function() {
    'use strict';

    angular.module('supplier', [])
        .controller('SupplierCtrl', SupplierCtrl);

    SupplierCtrl.$inject = ['$scope', '$state', 'supplierservice', 'dataShareService', 'toastr'];

    function SupplierCtrl($scope, $state, supplierservice, dataShareService, toastr) {
        var vm = this;

        vm.disable = true;

        vm.disableButton = function() {
            vm.disable = false;
        }

    }
})();