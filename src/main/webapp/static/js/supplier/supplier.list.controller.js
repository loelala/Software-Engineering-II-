/**
 * Controller for all suppliers (the list of suppliers).
 */
(function() {
    'use strict';

    angular.module('supplierList', [])
        .controller('SupplierListCtrl', SupplierListCtrl);

    SupplierListCtrl.$inject = ['supplierservice','dataShareService','toastr'];

    function SupplierListCtrl (supplierservice,dataShareService,toastr) {
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
                toastr.success('Added \"' + selectedSupplier['name'] + '\" to the checklist');
            }
            else
            {
                console.log('could not add')
                if(dataShareService.getSuppliers().length == 4)
                {
                    toastr.warning('Can\'t add more than 4 suppliers','Checklist full');
                }
                else
                {
                    toastr.warning('You can\'t add same supplier twice');
                }

            }

        }
        function removeSelected(index, selectedSupplier) {
            console.log("removing " + selectedSupplier);
            dataShareService.removeSupplier(selectedSupplier);
        }

    }

})();

