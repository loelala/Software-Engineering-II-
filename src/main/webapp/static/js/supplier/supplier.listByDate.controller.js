/**
 * Controller for all suppliers (the list of suppliers).
 */
(function() {
    'use strict';

    angular.module('supplierListByDate', [])
        .controller('SupplierListByDateCtrl', SupplierListByDateCtrl);

    SupplierListByDateCtrl.$inject = ['$filter','$state','supplierserviceByDate','dataShareService','toastr'];

    function SupplierListByDateCtrl ($filter,$state,supplierserviceByDate,dataShareService,toastr) {
        var vm = this;
        const formatString = "ddMMyyyy";
        vm.select = select;
        vm.removeSelected = removeSelected;
        vm.selectedRow = null;
        vm.selectedSupplierRow = null;
        dataShareService.eraseListByDate();
        vm.selectedSuppliers = dataShareService.getSuppliersByDate();
        vm.isTimeSelected = true;

        vm.goToComparison = function(){
            $state.go('comparison');
        };

        vm.byDateQuery = function(from, to) {
            var fromToPost = $filter('date')(from,formatString);
            var toToPost = $filter('date')(to,formatString);
            console.log(fromToPost);
            console.log(toToPost);
            vm.isTimeSelected = false;
            var allSuppliers = supplierserviceByDate.query({from:fromToPost,to:toToPost});
            allSuppliers.$promise.then(function(data) {
                console.log('get all suppliers by date', data);
                vm.supplierList = data;
            }, function(error) {
                console.log('some error occurred', error);
                errorFunction(error);
                toastr.error('Couldn\'t connect to the database','Database connection error!');
            });
        };

        function select(index, selectedSupplier) {
            vm.selectedRow = index;
            vm.idSelectedSupplier = selectedSupplier.id;
            console.log('selectedSupplier ID: ', vm.idSelectedSupplier);
            if(dataShareService.addSupplierByDate(selectedSupplier))
            {
                console.log("Size of selectedSuppliers " + vm.selectedSuppliers.length);
                console.log('added ' + selectedSupplier + ' to dataShareService');
                toastr.success('Added \"' + selectedSupplier['name'] + '\" to the checklist');
            }
            else
            {
                console.log('could not add');
                if(dataShareService.getSuppliersByDate().length == 4)
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
            console.log("removing index: "+ index + " object:  "  + selectedSupplier);
            dataShareService.removeSupplierByDate(selectedSupplier);
        }


        function errorFunction(error) {
            if(error.status === 500 && error.statusText === 'Internal Server Error') {
                vm.errorMail = 'help@wedoit.com';
                vm.error = 'There is no connection to the server! Please send an email to ' + vm.errorMail;
            }
        }

        // ========= DATEPICKER STUFF =============

        // Today for the To date selection
        vm.today = function() {
            vm.to = new Date();
        };

        vm.fromDate = function() {
            vm.form = new Date();
        };

        vm.today();
        vm.fromDate();

        vm.clear = function() {
            vm.from = null;
            vm.to = null;
        };

        // disable weekend selection
        vm.disableTo = function(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        };
        vm.disableFrom = function(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        };

        vm.maxDateFrom = new Date(2020, 5, 22);
        vm.maxDateTo = new Date(2020,5,22);
        vm.opens = [];

        /*$scope.$watch(function () {
         return vm.status.open;
         },function(value){
         vm.opens.push("valuationDatePickerIsOpen: " + value + " at: " + new Date());
         });*/


        vm.openFrom = function($event) {

            if ($event) {
                $event.preventDefault();
                $event.stopPropagation();
            }

            vm.status.openfrom = true;
        };

        vm.openTo = function($event) {

            if ($event) {
                $event.preventDefault();
                $event.stopPropagation();
            }

            vm.status.opento = true;
        };

        vm.setDateTo = function(year, month, day) {
            vm.to = new Date(year, month, day);
        };

        vm.setDateFrom = function(year, month, day) {
            vm.from = new Date(year, month, day);
        };

        vm.dateOptionsFrom = {
            formatYear: 'yy',
            startingDay: 1
        };

        vm.dateOptionsTo = {
            formatYear: 'yy',
            startingDay: 1
        };

        vm.formats = ['dd-MM-yyyy'];
        vm.format = vm.formats[0];

        vm.status = {
            opento: false,
            openfrom: false
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
