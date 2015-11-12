/**
 * Created by B3rni on 08.11.2015.
 */

(function() {
    'use strict';
    angular.module("DataShareServiceModule",[])
        .service('dataShareService', function() {
        var supplierList = [];

        var addSupplier = function(supplier) {
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

        var eraseList = function(){
            console.log("erased list");
            supplierList = [];
        }

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

        }

        return {
            addSupplier: addSupplier,
            getSuppliers: getSuppliers,
            eraseList : eraseList,
            removeSupplier : removeSupplier
        };

    });

})();
