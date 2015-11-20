(function () {
    'use strict';

    angular
        .module('login', [])
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', 'AuthenticationService','loginInformationHolderService'];
    function LoginController($state, AuthenticationService,loginInformationHolderService) {
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
            AuthenticationService.login(username, password)
                .then(function(data) { //success
                console.log('Login sucesss!');
                //localStorage.isLoggedIn = 'true';
                    loginInformationHolderService.updateIsLoggedIn(true);
                    var headers = data.headers();
                    console.log(data);
                    console.log(data.headers());
                    console.log(headers["wedoit-admin"]);
                if(headers["wedoit-admin"] === "true")
                {
                    console.log("is admin login from login controller");
                    loginInformationHolderService.updateIsAdmin(true);
                }
                else
                {
                    console.log("is NOTZ A admin login from login controller");
                    loginInformationHolderService.updateIsAdmin(false);

                }
                $state.transitionTo('home');
                vm.error = null;



            }, function() { //error
                console.log('error by login');
                vm.error = 'Username or password is incorrect!';
            });
        };
    }

})();
