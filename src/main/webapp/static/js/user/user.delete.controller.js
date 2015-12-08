(function () {
    'use strict';

    angular.module('deleteUser', [])
        .controller('DeleteUserCtrl', DeleteUserCtrl);

    DeleteUserCtrl.$inject = ['$scope','$uibModalInstance', 'UserService', 'user', 'toastr'];

    function DeleteUserCtrl($scope, $uibModalInstance, UserService, user, toastr) {
        var vm = this;

        $scope.user = user;

        $scope.deleteUser = deleteUser;
        $scope.cancel = cancel;

        function deleteUser() {
            console.log('delete user with ', $scope.user.surname);
            // TODO use right backend logic method here!!!
            UserService.deleteUser($scope.user).then(
                function() {
                    $uibModalInstance.close();
                    toastr.success('delete user ' + $scope.user.surname + 'successfully');
                }, function() {
                    toastr.error('Error!');
                    $uibModalInstance.close();
                }
            );
        }

        function cancel(){
            $uibModalInstance.close();
            $uibModalInstance.dismiss('cancel');
        }

    }
})();