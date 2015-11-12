(function () {
    'use strict';

    angular
        .module('login', [])
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', 'AuthenticationService'];
    function LoginController($state, AuthenticationService) {
        var vm = this;

        vm.username = '';
        vm.password = '';

        var getCookie= function(name) {
            var cookiename = name + "=";
            var ca = document.cookie.split(';');
            for(var i=0;i < ca.length;i++){
                var c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1,c.length);
                if (c.indexOf(cookiename) == 0) return c.substring(cookiename.length,c.length);
            }
            return null;
        };

        vm.isLoggedIn = function()
        {
            return getCookie("userid")!=null;
        };

        vm.login = function(username, password){
            AuthenticationService.login(username, password).then(function() {
                console.log('Login sucesss!');
                localStorage.isLoggedIn = 'true';
                $state.transitionTo('home');
                vm.error = null;
            }, function() {
                console.log('error by login');
                vm.error = 'Username or password is incorrect!';
            });
        };
    }

})();
