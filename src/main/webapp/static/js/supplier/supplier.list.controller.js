/**
 * Controller for all suppliers (the list of suppliers).
 */
(function() {
    'use strict';

    angular.module('supplierList', [])
        .controller('SupplierListCtrl', SupplierListCtrl);

    SupplierListCtrl.$inject = ['$scope','$state','supplierservice','dataShareService','toastr'];

    function SupplierListCtrl ($scope, $state,supplierservice,dataShareService,toastr) {
        var vm = this;

        vm.select = select;
        vm.removeSelected = removeSelected;
        vm.selectedRow = null;
        vm.selectedSupplierRow = null;
        dataShareService.eraseList();
        vm.selectedSuppliers = dataShareService.getSuppliers();

        vm.goToComparison = function () {
            $state.go('comparison');
        };

        var allSuppliers = supplierservice.query();
        allSuppliers.$promise.then(function (data) {
            console.log('get all suppliers', data);
            vm.supplierList = data;
        }, function (error) {
            console.log('some error occurred', error);
            errorFunction(error);
            toastr.error('Couldn\'t connect to the database', 'Database connection error!');
        });

        function select(index, selectedSupplier) {
            vm.selectedRow = index;
            vm.idSelectedSupplier = selectedSupplier.id;
            console.log('selectedSupplier ID: ', vm.idSelectedSupplier);
            if (dataShareService.addSupplier(selectedSupplier)) {

                console.log('added ' + selectedSupplier + ' to dataShareService');
                toastr.success('Added \"' + selectedSupplier['name'] + '\" to the checklist');
            }
            else {
                console.log('could not add')
                if (dataShareService.getSuppliers().length == 4) {
                    toastr.warning('Can\'t add more than 4 suppliers', 'Checklist full');
                }
                else {
                    toastr.warning('You can\'t add same supplier twice');
                }

            }

        }

        function removeSelected(selectedSupplier) {
            console.log("removing " + selectedSupplier);
            dataShareService.removeSupplier(selectedSupplier);
        }


        function errorFunction(error) {
            if (error.status === 500 && error.statusText === 'Internal Server Error') {
                vm.errorMail = 'help@wedoit.com';
                vm.error = 'There is no connection to the server! Please send an email to ' + vm.errorMail;
            }
        }
    }

})();
