(function() {
    'use strict';

    angular
        .module('datepicker.directive', [])
        .directive("mydatepicker", function () {
            return {
                controller: 'SupplierListByDateCtrl',
                restrict: "E", require: 'ngModel',
                scope: {
                    ngModel: "=?",
                    dateOptions: "=",
                    opened: "="
                },
                link: function ($scope, element, attrs) {
                    $scope.open = function (event, element, attrs) {
                        console.log("open");
                        event.preventDefault();
                        event.stopPropagation();
                        $scope.opened = true;
                    };

                    $scope.clear = function () {
                        $scope.ngModel = null;
                    };

                    scope.$watch(function () {
                        return ngModel.$modelValue;
                    }, function(newValue) {
                        console.log(newValue);
                    });

                },
                templateUrl: '/static/partials/datepicker.html'
            }
        });
})();