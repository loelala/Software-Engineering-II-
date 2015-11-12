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
            var displayData = vm.rawData;
            var dataArray = [];
            var supplierNameArray = [];
            vm.selectedRow = null;

            function buildDisplayData()
            {
                dataArray = [];
                supplierNameArray = [];
                displayData.forEach(function(entry) {
                    if(isNaN(entry["score"]))
                    {
                        dataArray.push(0);
                        supplierNameArray.push(entry["name"]);
                    }
                    else
                    {
                        dataArray.push(entry["score"]);
                        supplierNameArray.push(entry["name"]);
                    }

                });
            }


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


        function select(index, selectedSupplier) {

            var i = 0;
            var index = -1;
            for(i = 0 ; i < displayData.length ; i++)
            {

                if(selectedSupplier["id"] == displayData[i]["id"])
                {
                    index = i;
                }

            }

            if(index != -1 )
            {
                displayData.splice(index, 1);
            }
            else
            {
                displayData.push(selectedSupplier);
            }
            buildDisplayData();
        }
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