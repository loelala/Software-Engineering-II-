(function () {
    'use strict';

    angular
        .module('authenticationservice', [] )
        .service('AuthenticationService', AuthenticationService);


    AuthenticationService.$inject = ['$http'];

    function AuthenticationService ($http) {

        var service = {};

        service.login = function(username, password) {
            return $http.post('../api/user/login', {username: username, password: password}, {
                headers: {'Content-Type' : 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: {username: username, password: password}
            });
        };

        return service;
    }

})();
