(function () {
    'use strict';

    angular.module('editUser', [])
        .controller('EditUserCtrl', EditUserCtrl);

    EditUserCtrl.$inject = ['$scope','$uibModalInstance', 'UserService', 'user','toastr'];

    function EditUserCtrl($scope, $uibModalInstance, UserService, user, toastr) {
        var vm = this;

        $scope.user = user;

        console.log('User: ', vm.user);

        $scope.edit = edit;
        $scope.cancel = cancel;

        function edit() {
            console.log('delete user with ', $scope.user.surname);
            // TODO use right backend logic method here!!!
            UserService.edit($scope.user).then(
                function() {
                    toastr.success('edit user ' + $scope.user.surname + 'successfully');
                    $uibModalInstance.close();
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
