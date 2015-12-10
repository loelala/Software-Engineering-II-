(function() {
    'use strict';

    angular
        .module('supplier.filter', [])
        .filter('SupplierFilter', [function($filter) {
            return function(searchCriteria, inputArray, classType) {
                if(angular.isDefined(searchCriteria) || searchCriteria === '') {
                    return inputArray;
                }
                var data = [];
                angular.forEach(inputArray, function(item) {
                    if(item.classification === classType) {
                        if (item.name.toLowerCase().indexOf(searchCriteria.toLowerCase()) != -1 ){
                            data.push(item);
                        }
                    }
                });

                return data;
            }
        }]);
})();