(function () {
    'use strict';

    angular.module('newuser', [])
        .controller('UserController', UserController);

    UserController.$inject = ['$state','UserService'];
    function UserController($state,UserService) {
        var vm = this;

        vm.create = function(username, password){
            console.log("HALLLLLOOO");
            UserService.createNew(username,password)
                .then(function() {
                    console.log("creating a new user successfully");
                    $state.transitionTo("home");
                }, function() {
                    vm.error = "can not create a new user!!";
                });
        };
    }

})();