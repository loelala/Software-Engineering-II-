/**
 *  The bartChartController uses a external js library specially for anuglarjs to display
 *  the bar Chart on the BarChartPage.
 *  It uses the dataShareService to get the Information from the all supplier list
 */

(function() {
    'use strict';
    angular.module("barChartModule", ["chart.js"])
        .controller("barChartController", BarChartController);

    BarChartController.$inject = ['$state','dataShareService','toastr'];

    function BarChartController($state,dataShareService,toastr)
    {
            var vm = this;

            vm.rawData = dataShareService.getSuppliersByDate();
            var displayData = vm.rawData.slice();

<<<<<<< Temporary merge branch 1
            vm.dataArray = [];
            vm.dataArrayDetail = [];
            vm.supplierNameArray = [];
            vm.supplierNameArrayDetail = [];
            vm.selectedRow = [];
            vm.toggleChart = toggleChart;
            vm.displayScoreChart = true;
            function toggleChart(){
                if(vm.displayScoreChart)
                {
                    vm.displayScoreChart = false;
                }
                else
                {
                    vm.displayScoreChart = true;
                }
            }
            var deliveryDifference = ["Much too early", "too early", "on time", "too late", "much too late", "not calculated"];


            var colorArray = [];
            colorArray.push({fillColor: 'rgba(220,0,0,0.5)'});
            colorArray.push({fillColor: 'rgba(0,220,0,0.5)'});
            colorArray.push({fillColor: 'rgba(0,0,220,0.5)'});
            colorArray.push({fillColor: 'rgba(255,215,0,0.5)'});


            vm.goToSupplierList = goToSupplierList;

            function goToSupplierList(){
                $state.go('supplier');
            }

            var j;

            for(j = 0 ; j < vm.rawData.length ;j++)
            {
                vm.selectedRow[j] = true;
            }


            buildDisplayData();
            buildDisplayDataDetail();
            function buildDisplayData()
            {
                console.log("building Display Data");
                vm.dataArray = [];
                vm.supplierNameArray = [];
                vm.colours = [];

                displayData.forEach(function(entry) {
                    if(isNaN(entry["score"]))
                    {
                        vm.dataArray.push(0);
                        vm.supplierNameArray.push(entry["name"]);
                    }
                    else
                    {
                        vm.dataArray.push(entry["score"]);
                        vm.supplierNameArray.push(entry["name"]);
                    }
                })

                vm.labels = vm.supplierNameArray;
                vm.series = ['Score'];
                var i = 0;
                for(i = 0; i < displayData.length ; i++)
                {
                    console.log("adding color " + colorArray[i]);
                    vm.colours.push(colorArray[i]);
                }

                vm.data = [
                    vm.dataArray
                ];
            }

        function buildDisplayDataDetail()
        {

            var i;
            console.log("building Display Data detail");
            vm.dataArrayDetail = [];
            vm.labels_detail = [];
            vm.series_detail = [];
            vm.colours_detail = [];
            vm.data_detail = [];

            for(i = 0; i < displayData.length ; i++)
            {
                console.log("adding color " + colorArray[i]);
                vm.colours_detail.push(colorArray[i]);
            }

            for(i = 0 ; i < displayData.length ; i++)
            {
                vm.series_detail.push(displayData[i]["name"]); //supplier
                console.log("series push detail " + displayData[i]["name"]);
            }
            for(i = 0 ; i < deliveryDifference.length ; i++)
            {
                console.log("labelpushdetail" + deliveryDifference[i]);
                vm.labels_detail.push(deliveryDifference[i]); //supplier
            }

            console.log("deliverydifference.length: " + deliveryDifference.length);
            console.log("displaydata.length: " + displayData.length);

            for(i = 0 ; i < displayData.length ; i++)
            {
                var tmpArray = [];
                for( j = 0 ; j < deliveryDifference.length ; j++)
                {
                    tmpArray.push(displayData[i]["deliveryDifferences"][j]);
                    console.log("deliveryDifferencesdetail" + displayData[i]["deliveryDifferences"][j])

                }
                vm.dataArrayDetail.push(tmpArray);

            }
            console.log("dataArrayDetail length " + vm.dataArrayDetail.length);
            for(i = 0 ; i < vm.dataArrayDetail.length ; i++)
            {
                var k=0;
                console.log("dataArrayDetail length nested " + i + " " + vm.dataArrayDetail[i].length);
                for(k = 0 ; k < vm.dataArrayDetail[i].length ; k++)
                {
                    console.log(vm.dataArrayDetail[i][k]);
                }
            }

            for(i = 0 ; i < vm.dataArrayDetail.length ; i++)
            {
                vm.data_detail.push(vm.dataArrayDetail[i]);
            }
        }

        vm.removeSelected = function removeSelected(index, selectedSupplier) {
            console.log("remove Selected from View " + selectedSupplier);
            var i = 0;
            var check = -1;

            for(i = 0 ; i < vm.rawData.length ; i++)
            {
                if(i < displayData.length)
                {
                    if(selectedSupplier["id"] == displayData[i]["id"])
                    {
                        check = i;
                    }
                }
            }

            if(check != -1 )
            {
                console.log("splicing one out")

                var k = 0 ;
                var count = 0;
                for(k = 0 ; k < vm.selectedRow.length ; k++)
                {
                    if(vm.selectedRow[k] && k != index)
                    {
                        vm.selectedRow[index] = false;
                        break;
                    }
                    else
                    {
                        toastr.warning('Last supplier can\'t be deactivated');
                        console.log("last supplier cant be deactivated");
                    }
                }
            }
            else
            {
                console.log("pushing again to view")

                vm.selectedRow[index] = true;

            }
            recreate();
            buildDisplayData();
            buildDisplayDataDetail();
        };

        function recreate()
        {
            displayData = [];
            var i = 0;

            for(i = 0; i < vm.selectedRow.length; i++)
            {
                if(vm.selectedRow[i])
                {
                    displayData.push(vm.rawData[i])
                }
            }
        }
    }
})();
