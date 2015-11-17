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

        vm.goToComparison = function(){
            $state.go('comparison');
        }

        var allSuppliers = supplierservice.query();
        allSuppliers.$promise.then(function(data) {
            console.log('get all suppliers', data);
            vm.supplierList = data;
        }, function(error) {
            console.log('some error occurred', error);
            errorFunction(error);
            toastr.error('Couldn\'t connect to the database','Database connection error!');
        });

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


        function errorFunction(error) {
            if(error.status === 500 && error.statusText === 'Internal Server Error') {
                vm.errorMail = 'help@wedoit.com';
                vm.error = 'There is no connection to the server! Please send an email to ' + vm.errorMail;
            }
        }

        /*$scope.$watch('supplierList', function(row) {
         // get selected row
         row.filter(function(r) {
         if (r.isSelected) {
         console.log('HALLOOOO: ' , r);
         }
         })
         }, true);*/

        // TODO have to be implemented correctly for datepicking
        /*function getSupplierByDate(from, to) {
            var supplierByDate = $http.get('../..//all/between/:from/:to', {from: from, to: to})
                .then(function(response) {
                    console.log('getting data from server', response);
                    return supplierByDate;
                }, function(error) {
                    vm.error = error.data;
                })
        }*/


        // ========= DATEPICKER STUFF =============

        // Today for the To date selection
        vm.today = function() {
            vm.to = new Date();
            vm.form = new Date();
        };

        vm.today();

        vm.clear = function() {
            vm.from = null;
            vm.to = null;
        };

        // disable weekend selection
        vm.disable = function(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        };

        vm.maxDateFrom = new Date(2020, 5, 22);
        vm.maxDateTo = new Date(2020,5,22);
        vm.opens = [];

        $scope.$watch(function () {
            return vm.status.open;
        },function(value){
            vm.opens.push("valuationDatePickerIsOpen: " + value + " at: " + new Date());
        });

        vm.open = function($event) {

            if ($event) {
                $event.preventDefault();
                $event.stopPropagation(); // This is the magic
            }

            vm.status.open = true;
        };

        vm.setDateTo = function(year, month, day) {
            vm.to = new Date(year, month, day);
        };

        vm.setDateFrom = function(year, month, day) {
            vm.from = new Date(year, month, day);
        };

        vm.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };

        vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        vm.format = vm.formats[0];

        vm.status = {
            opened: false
        };

        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);

        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 2);
        vm.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];


    }

})();
