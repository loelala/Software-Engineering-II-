/**
 * the dataShareService is a service thats provides a data interchange between specific controller
 * for the barChartController and the supplerListController, so its possible to pass selected
 * supplier from the all suppliers page to the barchart-displaying Page.
 */
(function() {
    'use strict';
    angular.module("DataShareServiceModule",[])
        .service('dataShareService', function() {

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

        var getSuppliersByDate = function() {
            return supplierListByDate;
        };

        var eraseListByDate = function() {
            console.log('erased list by date');
            supplierListByDate = [];
        };

        var removeSupplierByDate = function(supplier) {
            var i = 0;
            var index = -1;
            for(i = 0 ; i < supplierListByDate.length ; i++) {
                console.log(supplierListByDate[i]);
                if(supplier["id"] == supplierListByDate[i]["id"]) {
                     index = i;
                }

            }

            if(index != -1) {
              supplierListByDate.splice(index, 1);
            }

        };


        return {
            addSupplierByDate: addSupplierByDate,
            getSuppliersByDate: getSuppliersByDate,
            eraseListByDate: eraseListByDate,
            removeSupplierByDate : removeSupplierByDate
        };

    });

})();
