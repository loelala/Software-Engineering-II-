/**
 * Important controller that handles all the input in the supplier overview
 * as well as adding the selected supplier to the dataShareService which is
 * necessary to get the data to the other controller (barChartController)
 */
(function() {
    'use strict';

    angular.module('supplierListByDate', [])
        .controller('SupplierListByDateCtrl', SupplierListByDateCtrl);

    SupplierListByDateCtrl.$inject = ['$filter','$state','supplierserviceByDate', 'supplierservice','dataShareService','toastr'];

    function SupplierListByDateCtrl ($filter,$state,supplierserviceByDate, supplierservice, dataShareService,toastr) {

        var vm = this;
        var formatString = "ddMMyyyy";

        vm.selectedRow = null;
        vm.selectedSupplierRow = null;
        dataShareService.eraseListByDate();
        vm.selectedSuppliers = dataShareService.getSuppliersByDate();
        vm.isTimeSelected = true;
        vm.isDateValid = false;
        vm.from = null;

        vm.select = select;
        vm.removeSelected = removeSelected;
        vm.byDateQuery = byDateQuery;
        vm.dateIsSelected = dateIsSelected;
        vm.goToComparison = goToComparison;



        var allSuppliers = supplierservice.query();
        allSuppliers.$promise.then(function(data) {
            vm.supplierList = data;
            console.log('get all suppliers');
        }, function(error) {
            console.log("some error occurred!", error);
            toastr.error('Couldn\'t connect to the database','Database connection error!');
        });

        function goToComparison(){
            $state.go('comparison');
        }

        function byDateQuery(from, to) {
            var fromToPost = $filter('date')(from,formatString);
            var toToPost = $filter('date')(to,formatString);
            console.log(fromToPost);
            console.log(toToPost);
            vm.isTimeSelected = false;
            var allSuppliersByDate = supplierserviceByDate.query({from:fromToPost,to:toToPost});
            allSuppliersByDate.$promise.then(function(data) {
                console.log('get all suppliers by date', data);
                vm.supplierList = data;
            }, function(error) {
                console.log('some error occurred', error);
                toastr.error('Couldn\'t connect to the database','Database connection error!');
            });
        }

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

        function dateIsSelected() {
            return vm.isDateValid === true;
        }

        // ========= DATEPICKER STUFF =============


        vm.maxDateFrom = new Date(2020, 5, 22);
        vm.maxDateTo = new Date(2020,5,22);
        vm.opens = []
        ;vm.dateOptionsFrom = {
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
            openTo: false,
            openFrom: false
        };

        vm.today = today();
        vm.fromDate = fromDate();
        vm.clear = clear;
        vm.disableWeekendTo = disableWeekendTo;
        vm.disableWeekendFrom = disableWeekendFrom;
        vm.dateIsSelected = false;

            /**
         * Set the date of TO to the date today.
         */
        function today() {
            vm.to = new Date();
        }

        function fromDate() {
            vm.dateIsSelected = true;
            vm.form = new Date();
        }

        /**
         * Set the dates to null
         */
        function clear() {
            vm.from = null;
            vm.to = null;
        }

        // disable weekend selection
        function disableWeekendTo(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        }
        function disableWeekendFrom(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        }

        vm.openFrom = function($event) {

            if ($event) {
                $event.preventDefault();
                $event.stopPropagation();
            }

            vm.status.openFrom = true;
        };

        vm.openTo = function($event) {

            if ($event) {
                $event.preventDefault();
                $event.stopPropagation();
            }

            vm.status.openTo = true;
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

        //====== NEW DATEPICKER =======

        /*vm.formData      = {
            formDate: '',
            toDate: 'hallo'
        };
        vm.openedTo = false;
        vm.openedFrom = false;

        //Datepicker
        vm.dateOptions = {
            'year-format': "'yy'",
            'show-weeks' : false
        };*/

    }

})();
