/**
 * This Controller handles the user creation, and passes the request to the backend the appropriate UserService
 */
(function () {
    'use strict';

    angular.module('newuser', [])
        .controller('UserController', UserController);

    UserController.$inject = ['$state','UserService','AllUserService','toastr', 'DeleteUserService', 'EditUserService'];
    function UserController($state,UserService,AllUserService,toastr, DeleteUserService, EditUserService) {
        var vm = this;

        vm.isNewUser = false;
        vm.isAllUsers = false;
        vm.userList = [];
        vm.newPassword = '';

        vm.create = create;
        vm.newUser = newUser;
        vm.allUsers = allUsers;
        vm.showAllCurrentUsers = showAllUsers;
        vm.editUser = editUser;
        vm.deleteUser = deleteUser;
        vm.checkIfAdmin = checkIfAdmin;
        vm.checkPassword = checkPassword;


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


        function editUser(index) {
            var user = vm.userList[index];
            EditUserService.editUser({username: user.name}, user).$promise.then(
                function() {
                    toastr.success("Edit " + user.name + " successfully!");
                }, function(error) {
                    console.log("some error occurred!", error);
                    toastr.error("Could not update user!");
                }
            );
            vm.newPassword = '';
            showAllUsers();
        }


        function deleteUser(index) {
            var user = vm.userList[index];
            DeleteUserService.deleteUser({username: user.name}, user).$promise.then(
                function() {
                    vm.userList.splice(index, 1);
                    toastr.success("Delete user from DB");
                }, function(error) {
                    console.log("some error occurred!", error);
                    toastr.error('Could not delete user!');
                }
            )
        }

        function checkIfAdmin(userName) {
            return userName === 'admin';
        }

        function checkPassword(data) {
            console.log("Length of pw: ", data.length);
            if (data.length <= 0) {
                return "Please enter a password"
            }
        }

    }

})();