(function () {
    'use strict';

    angular
        .module('login', [])
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', 'AuthenticationService'];
    function LoginController($state, AuthenticationService) {
        var vm = this;

        //AuthenticationService.clearCredentials();

        /*vm.login = function () {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function(response) {
                if (response.success) {
                    console.log('Hello you are logged in');
                    AuthenticationService.setCredentials(vm.username, vm.password);
                    $state.transitionTo('home');
                } else {
                    vm.error = response.message;
                    vm.dataLoading = false;
                }
            })
        };*/

        // have to be implemented
        /*vm.logout = function() {
            vm.dataLoading = true;
            AuthenticationService.Logout();
        };*/

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
            AuthenticationService.login(username, password);
        };

        vm.logout = function(){
            AuthenticationService.logout()
                .then(function(data) {
                    localStorage.removeItem('isLoggedIn');
                    $state.transitionTo('login');
                }, function(error) {
                    alert('Error!');
                });
        }

    }

})();
