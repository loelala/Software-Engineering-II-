/**
 * This is the module where we can define the functionality to connect to our backend.
 */
(function() {
    'use strict';

    angular.module('supplierserviceByDate', [])
        .service('supplierserviceByDate', supplierserviceByDate);

    supplierserviceByDate.$inject = ['$resource'];

    function supplierserviceByDate ($resource) {

        return $resource('../../api/supplier/all/between/:after/:before ', {}, {
            query: {method:'GET', params: {after: '@after', before: '@before'}, isArray: true}
        });
    }

})();