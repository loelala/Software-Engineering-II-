(function () {
    'use strict';

    angular
        .module('edituserservice', [])
        .factory('EditUserService', EditUserService);

    EditUserService.$inject = ['$resource', '$filter', '$q'];
    function EditUserService($resource, $filter, $q) {

        return $resource('../../api/user/edit', {}, {
            editUser: {
                method:'POST',
                isArray: false
            }
        });
    }
})();