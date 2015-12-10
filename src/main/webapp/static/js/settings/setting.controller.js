(function () {
    'use strict';

    angular.module('settings', [])
        .controller('SettingsCtrl', SettingsCtrl);

    SettingsCtrl.$inject = ['toastr', '$filter','classificationService','scoringService'];

    function SettingsCtrl(toastr, $filter,classificationService,scoringService) {
        var vm = this;

        vm.classificationSettings = false;
        vm.dateSettings = false;
        vm.scoringSettings = false;
        vm.saveClassification = saveClassification;
        vm.saveScoring = saveScoring;
        vm.editCompareRanges =  editCompareSettings;
        vm.editDateRanges = editDateRanges;
        vm.editScoring = editScoring;

        vm.classifications = {};
        vm.scoring = {};

        classificationService.getClassification().query()
            .$promise.then(function(data) {
                console.log('got Classification' + data);
                vm.classifications = data;
            }, function(error) {
                console.log('some error occurred', error);
                toastr.error('Couldn\'t fetch to the classification','Fetching error!');
            });

        scoringService.getScoring().query()
            .$promise.then(function(data){
                vm.scoring = data;
                console.log("got Scoring " +data);
            }, function(error){
                console.log("error fetching scoring " + error);
            });

        //vm.classifications = [
        //    {title: 'Top', fromVal: 4, toVal: 10},
        //    {title: 'Normal', fromVal: 11, toVal: 25},
        //    {title: 'One_Off', fromVal: 26, toVal: 10}
        //];

        vm.dateRanges =[
            {title: 'much too early', fromVal: 0, toVal: 0},
            {title: 'too early', fromVal: 0, toVal: 0},
            {title: 'on time', fromVal: 0, toVal: 0},
            {title: 'much too late', fromVal: 0, toVal: 0},
            {title: 'not calculated', fromVal: 0, toVal: 0}
        ];

        function editCompareSettings() {
            vm.classificationSettings = true;
            vm.dateSettings = false;
            vm.scoringSettings = false;
        }

        function editDateRanges() {
            vm.classificationSettings = false;
            vm.dateSettings = true;
            vm.scoringSettings = false;
        }

        function editScoring(){
            vm.classificationSettings = false;
            vm.dateSettings = false;
            vm.scoringSettings = true;
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

        function saveClassification() {
           classificationService.saveClassification(vm.classifications)
               .then(function(response){
                    console.log(response.status);
                   toastr.success('Classifications saved');
            },function(response){
                   console.log(response.status);
                   toastr.error('Failed to save classifications');
               });
        };

        function saveScoring(){
            scoringService.saveScoring(vm.scoring)
                .then(function (response) {
                    console.log(response.status);
                    toastr.success('Scores saved');
                },function(response){
                    console.log(response.status);
                    toastr.error('Failed to save scores');
                });
        }

        vm.saveDateRanges = function(index, data) {
            console.log(data);
            vm.dateRanges[index].fromVal = data.fromVal;
            vm.dateRanges[index].toVal = data.toVal;
            toastr.success('Saved classification range for ' + vm.dateRanges[index].title);
        }

    }
})();