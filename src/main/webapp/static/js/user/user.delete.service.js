(function () {
    'use strict';

    angular
        .module('deleteuserservice', [])
        .factory('DeleteUserService', DeleteUserService);

    DeleteUserService.$inject = ['$resource', '$filter', '$q'];
    function DeleteUserService($resource, $filter, $q) {

        return $resource('../../api/user/delete/:username', {}, {
            deleteUser: {
                method:'DELETE',
                params: {username: 'username'},
                isArray: false
            }
        });
    }
})();