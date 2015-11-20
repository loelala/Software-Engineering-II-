/**
 * Controller for a specific supplier.
 */
(function() {
   'use strict';

    angular.module('supplierDetails', [])
        .controller('SupplierDetailsCtrl', SupplierDetailsCtrl);

    SupplierDetailsCtrl.$inject = ['supplierservice'];

    function SupplierDetailsCtrl (supplierservice) {
        var vm = this;

        /* Here we have do define the route to our backend */
        var Supplier = supplierservice();

        vm.supplier = supplier;

        function supplier(supplierId) {
            var supplier = Supplier.get({id: supplierId});
            supplier.$promise.then(function() {
                vm.selecteSupplier = supplier;
            }, function(error) {
                vm.error = error.data;
            })
        }
    }
})();