/**
 * Created by B3rni on 07.11.2015.
 */

(function() {
    'use strict';
    angular.module("BarChartModule", ["chart.js"])
        .controller("BarChartController", BarChartController);

    BarChartController.$inject = ['dataShareService'];

    function BarChartController(dataShareService)
    {
            var vm = this;

            vm.rawData = dataShareService.getSuppliers();
            var dataArray = [];
            var supplierNameArray = [];
            vm.rawData.forEach(function(entry) {
                dataArray.push(entry["score"]);
                supplierNameArray.push(entry["name"]);
            });

            supplierNameArray.forEach(function(entry){
                console.log(entry);
            })

            console.log("raw data" + dataShareService.getSuppliers());

            vm.labels = supplierNameArray;
            vm.series = ['Score'];
            vm.colours = [{
                fillColor: 'rgba(220,0,0,0.5)'
            }];
            vm.data = [
                dataArray
            ];
    };
})();
/**
// * Created by B3rni on 07.11.2015.
// */
//
//(function() {
//    'use strict';
//    angular.module("BarChartModule", ["chart.js", "dataShareService"])
//        .controller("BarChartController", function ($scope,dataShareService) {
//
//
//
//
//            $scope.labels = ['Supploer A', 'Supplier B', 'Supplier C', 'Supplier D'];
//            $scope.series = ['Score'];
//            $scope.colours = [{
//                fillColor: 'rgba(220,0,0,0.5)'
//            }];
//            $scope.data = [
//                [9, 8, 7, 6]
//            ];
//        });
//})();