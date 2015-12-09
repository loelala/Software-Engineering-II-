(function () {
    'use strict';

    angular.module('settings', [])
        .controller('SettingsCtrl', SettingsCtrl);

    SettingsCtrl.$inject = ['toastr', '$filter'];

    function SettingsCtrl(toastr, $filter) {
        var vm = this;

        vm.classificationSettings = false;
        vm.dateSettings = false;

        vm.editCompareRanges =  editCompareSettings;
        vm.editDateRanges = editDateRanges;

        vm.classifications = [
            {title: 'Top', fromVal: 4, toVal: 10},
            {title: 'Normal', fromVal: 11, toVal: 25},
            {title: 'One_Off', fromVal: 26, toVal: 10}
        ];

        vm.dateRanges =[
            {title: 'much too early', fromVal: 0, toVal: 0},
            {title: 'too early', fromVal: 0, toVal: 0},
            {title: 'on time', fromVal: 0, toVal: 0},
            {title: 'much too late', fromVal: 0, toVal: 0},
            {title: 'not calculated', fromVal: 0, toVal: 0}
        ];

        function editCompareSettings() {
            vm.classificationSettings = vm.classificationSettings !== true;
            if(vm.classificationSettings){
                vm.dateSettings = false;
            }
        }

        function editDateRanges() {
            vm.dateSettings = vm.dateSettings !== true;
            if (vm.dateSettings) {
                vm.classificationSettings = false;
            }
        }

        vm.checkFromCl= function(index,data) {
            console.log('index: ', index);
            var currentToValue = vm.classifications[index].fromVal;
            console.log("current to value: ", currentToValue);
            if (data >= currentToValue) {
                return 'From value have to be smaller than To value!';
            }

        };

        vm.checkToCl = function(index,data) {
            console.log('index: ', index);
            var currentFromValue = vm.classifications[index].fromVal;
            console.log("current to value: ", currentFromValue);
            if (data <= currentFromValue) {
                return 'To value have to be greater than From value!'
            }
        };

        // TODO add date range validation

        vm.checkFromDr = function(index,data) {

        };

        vm.checkToDr = function(index,data) {

        };

        vm.saveClassification = function(index, data) {
           vm.classifications[index].fromVal = data.fromVal;
           vm.classifications[index].toVal = data.toVal;
            toastr.success('Saved classification range for ' + vm.classifications[index].title);
        };

        vm.saveDateRanges = function(index, data) {
            console.log(data);
            vm.dateRanges[index].fromVal = data.fromVal;
            vm.dateRanges[index].toVal = data.toVal;
            toastr.success('Saved classification range for ' + vm.dateRanges[index].title);
        }

    }
})();