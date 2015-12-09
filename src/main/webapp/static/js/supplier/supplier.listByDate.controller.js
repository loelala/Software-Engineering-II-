/**
 * Important controller that handles all the input in the supplier overview
 * as well as adding the selected supplier to the dataShareService which is
 * necessary to get the data to the other controller (barChartController)
 */
(function() {
    'use strict';

    angular.module('supplierListByDate', [])
        .controller('SupplierListByDateCtrl', SupplierListByDateCtrl);

    SupplierListByDateCtrl.$inject = ['$scope','$filter','$state','supplierserviceByDate', 'supplierservice','dataShareService','toastr'];

    function SupplierListByDateCtrl ($scope,$filter,$state,supplierserviceByDate, supplierservice, dataShareService,toastr) {

        var vm = this;
        var formatString = "DDMMYYYY";

        vm.selectedRow = null;
        vm.selectedSupplierRow = null;
        dataShareService.eraseListByDate();
        vm.selectedSuppliers = dataShareService.getSuppliersByDate();
        vm.isTimeSelected = true;
        vm.isDateValid = false;
        vm.from = null;
        vm.isCollapsed = true;
        vm.itemsByPage = 10;

        vm.supplierList = [];
        vm.classifications = [ {id: 1, name: 'TOP'}, {id: 2, name:'NORMAL'}, {id: 3, name:'ONE_OFF'}, {id: 4, name:'NONE'}];
        vm.formData = {};
        vm.date = {
            // from
            startDate: moment().subtract(7,"days"),
            // to
            endDate: moment()
        };


        vm.dataOptions = {
            locale: {
                applyClass: 'btn-green',
                applyLabel: "Apply",
                fromLabel: "From",
                format: "DD-MM-YYYY",
                toLabel: "To",
                cancelLabel: 'Cancel',
                customRangeLabel: 'Custom range'
            },
            ranges: {
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'Last half year': [moment().subtract(182, 'days'), moment()],
                'Last Year': [moment().subtract(364, 'days'), moment()]
            }
        };

        vm.select = select;
        vm.removeSelected = removeSelected;
        vm.byDateQuery = byDateQuery;
        vm.dateIsSelected = dateIsSelected;
        vm.goToComparison = goToComparison;

        supplierservice.query().$promise.then(function(data) {
            vm.supplierList = data;
            console.log('get all suppliers', vm.supplierList);

            vm.displayedCollection = [].concat(vm.supplierList);
            console.log('get all suppliers to show', vm.displayedCollection);
        }, function(error) {
            console.log("some error occurred!", error);
            toastr.error('Couldn\'t connect to the database','Database connection error!');
        });

        function goToComparison(){
            $state.go('comparison');
        }

        function byDateQuery(from, to) {

            var fromToPost = from.format(formatString);
            var toToPost = to.format(formatString);
            console.log('From: ',fromToPost);
            console.log('To:', toToPost);
            vm.isTimeSelected = false;
            var allSuppliersByDate = supplierserviceByDate.query({from:fromToPost, to:toToPost});
            allSuppliersByDate.$promise.then(function(data) {
                console.log('get all suppliers by date', data);
                vm.supplierList = data;
                vm.displayedCollection = [].concat(vm.supplierList);
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

        //Watch for date changes
        $scope.$watch('vm.date', function(newDate) {
            console.log('New date set: ', newDate);
        }, false);

    }

})();
