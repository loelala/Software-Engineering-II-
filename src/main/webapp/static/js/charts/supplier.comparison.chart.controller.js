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

            vm.dataArray = [];
            vm.dataArrayDetail = [];
            vm.supplierNameArray = [];
            vm.supplierNameArrayDetail = [];
            vm.selectedRow = [];

            var colorArray = [{
                fillColor: 'rgba(220,0,0,0.5)' //red
            },
            {
                fillColor: 'rgba(0,220,0,0.5)' //green
            },
            {
                fillColor: 'rgba(0,0,220,0.5)' //blue
            },{
                fillColor: 'rgba(255,215,0,0.5)' //gold
            }]

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
                console.log("building Display DAta");
                vm.dataArray = [];
                vm.supplierNameArray = [];
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
                for(i = 0; i < colorArray.length ; i++)
                {
                    vm.colours[i] = colorArray[i];
                }
                //vm.colours = [{
                //    fillColor: 'rgba(220,0,0,0.5)'
                //}];
                vm.data = [
                    vm.dataArray
                ];
            }

        function buildDisplayDataDetail()
        {
            console.log("building Display DAta");
            vm.dataArrayDetail = [];
            vm.supplierNameArrayDetail = [];
            displayData.forEach(function(entry) {
                if(isNaN(entry["score"]))
                {
                    vm.dataArrayDetail.push(0);
                    vm.supplierNameArrayDetail.push(entry["name"]);
                }
                else
                {
                    vm.dataArrayDetail.push(entry["score"]);
                    vm.supplierNameArrayDetail.push(entry["name"]);
                }

            });

            vm.labels_detail = vm.supplierNameArrayDetail;
            //vm.series_detail = ['Score'];

            for(i = 0; i < colorArray.length ; i++)
            {
                vm.colours[i] = colorArray[i];
            }
            vm.data_detail = [
                vm.dataArray
            ];
        }

            console.log("raw data" + dataShareService.getSuppliers());




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
