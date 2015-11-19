(function () {
    'use strict';

    angular
        .module('userservice', [])
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$filter', '$q'];
    function UserService($http, $filter, $q) {

        var service = {};

        service.createNew = function (username, password) {
            return $http.post('../api/user/new', {username: username, password: password}, {
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: {username: username, password: password}
            });
        };

        return service;
    }
})();