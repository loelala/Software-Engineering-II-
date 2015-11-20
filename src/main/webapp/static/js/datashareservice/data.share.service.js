/**
 * the dataShareService is a service thats provides a data interchange between specific controller
 * for the barChartController and the supplerListController, so its possible to pass selected
 * supplier from the all suppliers page to the barchart-displaying Page.
 */
(function() {
    'use strict';
    angular.module("DataShareServiceModule",[])
        .service('dataShareService', function() {

        //var supplierList = [];
        var supplierListByDate = [];

        var addSupplierByDate = function(supplier) {
                if(supplierListByDate.length >= 4)
                {
                    console.log("not adding supplier, cant have more than 4");
                    return false;
                }
                else
                {
                    console.log("adding new Supplier" + supplier)
                    var i = 0;
                    var index = -1;
                    for(i = 0 ; i < supplierListByDate.length ; i++)
                    {
                        console.log(supplierListByDate[i]);
                        if(supplier["id"] == supplierListByDate[i]["id"])
                        {
                            index = i;
                        }

                    }
                    if(index == -1)
                    {
                        supplierListByDate.push(supplier);
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                }

        };

        //var addSupplier = function(supplier) {
            //    if(supplierList.length >= 4)
            //    {
            //        console.log("not adding supplier, cant have more than 4");
            //        return false;
            //    }
            //    else
            //    {
            //        console.log("trying to add new Supplier" + supplier);
            //        var i = 0;
            //        var index = -1;
            //        for(i = 0 ; i < supplierList.length ; i++)
            //        {
            //            console.log(supplierList[i]);
            //            if(supplier["id"] == supplierList[i]["id"])
            //            {
            //                index = i;
            //            }
            //
            //        }
            //        if(index == -1)
            //        {
            //            supplierList.push(supplier);
            //            console.log("current size of supplerList in datashareservice is: " + supplierList.length)
            //            return true;
            //        }
            //        else
            //        {
            //            console.log("did not add the supplier for reasons");
            //            return false;
            //        }
            //
            //    }
            //
            //};

        //var getSuppliers = function(){
        //    return supplierList;
        //};

        var getSuppliersByDate = function() {
            return supplierListByDate;
        };

        //var eraseList = function(){
        //    console.log("erased list");
        //    supplierList = [];
        //};
        var eraseListByDate = function() {
            console.log('erased list by date');
            supplierListByDate = [];
        };

        //var removeSupplier = function(supplier)
        //{
        //    var i = 0;
        //    var index = -1;
        //    for(i = 0 ; i < supplierList.length ; i++)
        //    {
        //        console.log(supplierList[i]["id"] + " ==? " +supplier["id"]);
        //        if(supplier["id"] == supplierList[i]["id"])
        //        {
        //            console.log("found supplier at array index " + i);
        //            index = i;
        //        }
        //
        //    }
        //    if(index != -1)
        //    {
        //        console.log("splicing out " + supplier);
        //        supplierList.splice(index, 1);
        //    }
        //    else
        //    {
        //        console.log("did not splice out anything! should not happen")
        //    }
        //
        //};
            var removeSupplierByDate = function(supplier)
            {
                var i = 0;
                var index = -1;
                for(i = 0 ; i < supplierListByDate.length ; i++)
                {
                    console.log(supplierListByDate[i]);
                    if(supplier["id"] == supplierListByDate[i]["id"])
                    {
                        index = i;
                    }

                }
                if(index != -1)
                {
                    supplierListByDate.splice(index, 1);
                }

            };


        return {
            //addSupplier: addSupplier,
            addSupplierByDate: addSupplierByDate,
            //getSuppliers: getSuppliers,
            getSuppliersByDate: getSuppliersByDate,
            //eraseList : eraseList,
            eraseListByDate: eraseListByDate,
            //removeSupplier : removeSupplier,
            removeSupplierByDate : removeSupplierByDate
        };

    });

})();
