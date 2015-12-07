(function () {
    'use strict';

    angular.module('editUser', [])
        .controller('EditUserCtrl', EditUserCtrl);

    EditUserCtrl.$inject = ['$uibModalInstance', 'UserService'];
    function EditUserCtrl($uibModalInstance, UserService, user) {
        var vm = this;

        vm.user = user;



    }
})();
