/**
 * This is the module where we can define the functionality to connect to our backend.
 */
(function() {
    'use strict';

    angular.module('indexservice', [])
        .service('indexservice', indexservice);

    indexservice.$inject = ['$resource'];

    function indexservice ($resource) {

        var service = $resource('../../api/vendor/:id', {}, {
            query: {method:'GET', params: {id: 'all'}, isArray: true},
            get: {method:'GET', params: {id:'@id'}, isArray: false}
        });

        return service;
    }

})();
