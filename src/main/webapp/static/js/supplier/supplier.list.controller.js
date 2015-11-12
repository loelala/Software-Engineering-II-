/**
 * Controller for all suppliers (the list of suppliers).
 */
(function() {
    'use strict';

    angular.module('supplierList', [])
        .controller('SupplierListCtrl', SupplierListCtrl);

    SupplierListCtrl.$inject = ['supplierservice','dataShareService'];

    function SupplierListCtrl (supplierservice,dataShareService) {
        var vm = this;
        vm.fallbackData = 
        vm.select = select;
        vm.removeSelected = removeSelected;
        vm.selectedRow = null;
        vm.selectedSupplierRow = null;
        dataShareService.eraseList();
        vm.selectedSuppliers = dataShareService.getSuppliers();

        var allSuppliers = supplierservice.query();
        allSuppliers.$promise.then(function(data) {
            console.log('get all suppliers', data);
            vm.supplierList = data;
        }, function(error) {
            console.log('some error occurred', error);
            vm.error = error;
        })

        function select(index, selectedSupplier) {
            vm.selectedRow = index;
            vm.idSelectedSupplier = selectedSupplier.id;
            console.log('selectedSupplier ID: ', vm.idSelectedSupplier);
            if(dataShareService.addSupplier(selectedSupplier))
            {
                console.log('added ' + selectedSupplier + ' to dataShareService');
            }
            else
            {
                console.log('could not add')
            }

        }
        function removeSelected(index, selectedSupplier) {
            console.log("removing " + selectedSupplier);
            dataShareService.removeSupplier(selectedSupplier);
        }

    }

})();

