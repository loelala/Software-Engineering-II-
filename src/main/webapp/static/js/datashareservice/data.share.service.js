/**
 * Created by B3rni on 08.11.2015.
 */

(function() {
    'use strict';
    angular.module("DataShareServiceModule",[])
        .service('dataShareService', function() {

        var supplierList = [];
        var supplierListByDate = [];

        var addSupplierByDate = function(supplier) {
                if(supplierList.length >= 4)
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

        var addSupplier = function(supplier) {
            if(supplierList.length >= 4)
            {
                console.log("not adding supplier, cant have more than 4");
                return false;
            }
            else
            {
                console.log("adding new Supplier" + supplier);
                var i = 0;
                var index = -1;
                for(i = 0 ; i < supplierList.length ; i++)
                {
                    console.log(supplierList[i]);
                    if(supplier["id"] == supplierList[i]["id"])
                    {
                        index = i;
                    }

                }
                if(index == -1)
                {
                    supplierList.push(supplier);
                    return true;
                }
                else
                {
                    return false;
                }

            }

        };

        var getSuppliers = function(){
            return supplierList;
        };

        var getSuppliersByDate = function() {
            return supplierListByDate;
        };

        var eraseList = function(){
            console.log("erased list");
            supplierList = [];
        };
        var eraseListByDate = function() {
            console.log('erased list by date');
            supplierListByDate = [];
        };

        var removeSupplier = function(supplier)
        {
            var i = 0;
            var index = -1;
            for(i = 0 ; i < supplierList.length ; i++)
            {
                console.log(supplierList[i]);
                if(supplier["id"] == supplierList[i]["id"])
                {
                    index = i;
                }

            }
            if(index != -1)
            {
                supplierList.splice(index, 1);
            }

        };
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
            addSupplier: addSupplier,
            addSupplierByDate: addSupplierByDate,
            getSuppliers: getSuppliers,
            getSuppliersByDate: getSuppliersByDate,
            eraseList : eraseList,
            eraseListByDate: eraseListByDate,
            removeSupplier : removeSupplier,
            removeSupplierByDate : removeSupplierByDate
        };

    });

})();
