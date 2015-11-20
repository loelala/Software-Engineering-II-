/**
 *
 */
(function() {
    'use strict';

    angular.module('supplierservice', [])
        .service('supplierservice', supplierservice);

    supplierservice.$inject = ['$resource'];

    function supplierservice ($resource) {

        return $resource('../../api/supplier/:id', {}, {
            query: {method:'GET', params: {id: 'all'}, isArray: true}
        });
    }

})();