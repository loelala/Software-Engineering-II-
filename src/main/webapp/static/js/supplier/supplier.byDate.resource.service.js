/**
 * This is the module where we can define the functionality to connect to our backend.
 */
(function() {
    'use strict';

    angular.module('supplierserviceByDate', [])
        .service('supplierserviceByDate', supplierserviceByDate);

    supplierserviceByDate.$inject = ['$resource'];

    function supplierserviceByDate ($resource) {

        var service = $resource('../../api/supplier/all/between/:from/:to ', {}, {
            query: {
                method:'GET',
                params: {from: 'from', to: 'to'},
                isArray: true
            }
        });

        return service;
    }

})();