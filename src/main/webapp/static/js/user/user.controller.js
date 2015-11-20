/**
 * This Controller handles the user creation, and passes the request to the backend the appropriate UserService
 */
(function () {
    'use strict';

    angular.module('newuser', [])
        .controller('UserController', UserController);

    UserController.$inject = ['$state','UserService','toastr'];
    function UserController($state,UserService,toastr) {
        var vm = this;

        vm.create = function(username, password){

            UserService.createNew(username,password)
                .then(function() {
                    console.log("creating a new user successfully");
                    toastr.success('Added \"' + username + '\" to Database, you can now log in with this user');
                }, function() {
                    vm.error = "can not create a new user!!";
                    toastr.error('Could not creater user! No Duplicates, No Empty Passwords!');
                });
        };
    }

})();