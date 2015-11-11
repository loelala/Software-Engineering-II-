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
            }
            else
            {
                console.log("adding new Supplier" + supplier)
                supplierList.push(supplier);
            }

        };

        var getSuppliers = function(){
            return supplierList;
        };

        var eraseList = function(){
            console.log("erased list");
            supplierList = [];
        };

        return {
            addSupplier: addSupplier,
            getSuppliers: getSuppliers,
            eraseList : eraseList
        };

    });

})();
