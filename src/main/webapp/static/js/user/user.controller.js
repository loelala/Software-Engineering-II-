/**
 * This Controller handles the user creation, and passes the request to the backend the appropriate UserService
 */
(function () {
    'use strict';

    angular.module('newuser', [])
        .controller('UserController', UserController);

    UserController.$inject = ['$state','UserService','AllUserService','toastr', 'loginInformationHolderService'];
    function UserController($state,UserService,AllUserService,toastr, loginInformationHolderService) {
        var vm = this;

        vm.isNewUser = false;
        vm.isAllUsers = false;
        vm.userList = [];

        vm.create = create;
        vm.newUser = newUser;
        vm.allUsers = allUsers;
        vm.showAllCurrentUsers = showAllUsers;
        vm.editUser = editUser;
        vm.deleteUser = deleteUser;
        vm.checkIfAdmin = checkIfAdmin;

        function create (username, password, email, name){
            UserService.createNew(username,password, email, name)
                .then(function() {
                    console.log("creating a new user successfully");
                    toastr.success('Added \"' + username + '\" to Database, you can now log in with this user');
                    $state.go('useradmin');
                }, function(error) {
                    console.log(error);
                    vm.error = "can not create a new user!!";
                    toastr.error('Could not creater user! No Duplicates, No Empty Passwords!');
                });
        }

        function newUser() {
            vm.isNewUser = vm.isNewUser !== true;
            if(vm.isNewUser){
                vm.isAllUsers = false;
            }
        }

        function allUsers() {
            vm.isAllUsers = vm.isAllUsers !== true;
            if(vm.isAllUsers){
                vm.isNewUser = false;
                showAllUsers();
            }
        }

        function showAllUsers() {
            AllUserService.query().$promise.then(
                function(data){
                    console.log('All Users: ', data);
                    vm.userList = data;
                },
                function(error) {
                    console.log(error);
                    toastr.error('Could not get all users', 'Server Error');
                }
            )
        }


        function editUser(index,user) {
            vm.userList[index].surname = user.surname;
            vm.userList[index].email = user.emailAddress;
            // TODO API call to update user data
        }


        function deleteUser(index) {
            vm.userList.splice(index, 1);
            // TODO delete user with api call
        }


        function checkIfAdmin(userName) {
            return userName === 'admin';
        }


    }

})();