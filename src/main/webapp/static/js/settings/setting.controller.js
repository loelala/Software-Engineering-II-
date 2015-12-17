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

        vm.classifications = {};
        vm.scoring = {};

        vm.saveClassification = saveClassification;
        vm.saveScoring = saveScoring;
        vm.editCompareRanges =  editCompareSettings;
        vm.editDateRanges = editDateRanges;
        vm.editScoring = editScoring;
        vm.saveDateRanges = saveDateRanges;
        vm.defaultScoring = enableDefaultScoring;
        vm.defaultRanges = enableDefaultRanges;

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

        function enableDefaultScoring() {
            vm.scoring.scoringLimits['PMAX100'] = 0;
            vm.scoring.scoringLimits['NMIN100'] = -1;
            vm.scoring.scoringLimits['PMAX90'] = 3;
            vm.scoring.scoringLimits['NMIN90'] = -2;
            vm.scoring.scoringLimits['PMAX80'] = 7;
            vm.scoring.scoringLimits['NMIN80'] = -3;
            vm.scoring.scoringLimits['PMAX60'] = 14;
            vm.scoring.scoringLimits['NMIN60'] = -7;
            vm.scoring.scoringLimits['PMAX40'] = 28;
            vm.scoring.scoringLimits['NMIN40'] = -10;
        }


        function enableDefaultRanges() {
            vm.classifications.classificationLimits['ONE_OFF'] = 1;
            vm.classifications.classificationLimits['NORMAL'] = 19;
        }

        function saveClassification() {
           classificationService.saveClassification(vm.classifications)
               .then(function(response){
                    console.log(response.status);
                   toastr.success('Classifications saved');
            },function(response){
                   console.log(response.status);
                   toastr.error('Failed to save classifications');
               });
        }

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

        function saveDateRanges(index, data) {
            console.log(data);
            vm.dateRanges[index].fromVal = data.fromVal;
            vm.dateRanges[index].toVal = data.toVal;
            toastr.success('Saved classification range for ' + vm.dateRanges[index].title);
        }

    }
})();