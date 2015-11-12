(function() {
    'use strict';

    angular
        .module('dateRange.directive', [])
        .directive('stDateRange', ['$timeout', function ($timeout) {
            return {
                restrict: 'E',
                require: '^stTable',
                scope: {
                    before: '=',
                    after: '='
                },
                templateUrl: 'stDateRange.html',

                link: function (scope, element, attr, table) {

                    var inputs = element.find('input');
                    var inputBefore = ng.element(inputs[0]);
                    var inputAfter = ng.element(inputs[1]);
                    var predicateName = attr.predicate;


                    [inputBefore, inputAfter].forEach(function (input) {

                        input.bind('blur', function () {


                            var query = {};

                            if (!scope.isBeforeOpen && !scope.isAfterOpen) {

                                if (scope.before) {
                                    query.before = scope.before;
                                }

                                if (scope.after) {
                                    query.after = scope.after;
                                }

                                scope.$apply(function () {
                                    table.search(query, predicateName);
                                })
                            }
                        });
                    });

                    function open(before) {
                        return function ($event) {
                            $event.preventDefault();
                            $event.stopPropagation();

                            if (before) {
                                scope.isBeforeOpen = true;
                            } else {
                                scope.isAfterOpen = true;
                            }
                        }
                    }

                    scope.openBefore = open(true);
                    scope.openAfter = open();
                }
            }
        }]);

})();