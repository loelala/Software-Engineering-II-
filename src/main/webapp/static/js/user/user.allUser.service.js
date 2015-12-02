(function () {
    'use strict';

    angular
        .module('alluserservice', [])
        .factory('AllUserService', AllUserService);

    AllUserService.$inject = ['$resource', '$filter', '$q'];
    function AllUserService($resource, $filter, $q) {

        return $resource('../../api/user/all', {}, {
            query: {method:'GET', isArray: true}
        });
    }
})();
