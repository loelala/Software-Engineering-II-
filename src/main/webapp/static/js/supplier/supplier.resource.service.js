/**
 * This is the module where we can define the functionality to connect to our backend.
 */
(function() {
    'use strict';

    angular.module('supplierservice', [])
        .service('supplierservice', supplierservice);

    supplierservice.$inject = ['$resource'];

    function supplierservice ($resource) {

        var service = $resource('../../api/supplier/:id', {}, {
            query: {method:'GET', params: {id: 'all'}, isArray: true},
            get: {method:'GET', params: {id:'@id'}, isArray: false}
        });

        return service;
    }

})();