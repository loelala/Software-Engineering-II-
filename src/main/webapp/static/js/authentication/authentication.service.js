(function () {
    'use strict';

    angular
        .module('authenticationservice', [] )
        .service('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$resource', '$cookieStore','$q', '$window'];

    function AuthenticationService ($resource, $q, $window) {

        var userInfo;

        function Login(username, password) {

            var deferred = $q.defer();

            $resource.post("/api/user/login", {
                username: username,
                password: password
            }).then(function(result) {
                userInfo = {
                    accessToken: result.data.access_token,
                    username: result.data.username
                };
                $window.sessionStorage['userInfo'] = JSON.stringify(userInfo);
                deferred.resolve(userInfo);
            }, function(error) {
                deferred.reject(error);
            });

            return deferred.promise;

        }

        function getUserInfo() {
            return userInfo;
        }

        function init() {
            if ($window.sessionStorage['userInfo']) {
                userInfo = JSON.parse($window.sessionStorage['userInfo']);
            }
            console.log('init is called!');
        }

        init();

        return {
            login: Login,
            getUserInfo: getUserInfo()
        };
    }

})();
