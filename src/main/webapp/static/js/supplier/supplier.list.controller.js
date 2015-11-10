/**
 * Controller for all suppliers (the list of suppliers).
 */
(function() {
    'use strict';

    angular.module('supplierList', [])
        .controller('SupplierListCtrl', SupplierListCtrl)
        .directive('csSelect', select)
        .directive('stSelectRow', selectRow);

    SupplierListCtrl.$inject = ['$scope','supplierservice','dataShareService'];

    function SupplierListCtrl (supplierservice, dataShareService) {
        var vm = this;

        vm.select = select;
        vm.selectedRow = null;
        dataShareService.eraseList();


        var allSuppliers = supplierservice.query();
        allSuppliers.$promise.then(function(data) {
            console.log('get all suppliers', data);
            vm.supplierList = data;
        }, function(error) {
            console.log('some error occurred', error);
            errorFunction(error);
        });

        function select(index, selectedSupplier) {
            vm.selectedRow = index;
            vm.idSelectedSupplier = selectedSupplier.id;
            console.log('selectedSupplier ID: ', vm.idSelectedSupplier);
            dataShareService.addSupplier(selectedSupplier);
            console.log('added ' + selectedSupplier + ' to dataShareService');
        }


        function errorFunction(error) {
            if(error.status === 500 && error.statusText === 'Internal Server Error') {
                vm.errorMail = 'help@wedoit.com';
                vm.error = 'There is no connection to the server! Please send an email to ' + vm.errorMail;
            }
        }

        /*$scope.$watch('supplierList', function(row) {
         // get selected row
         row.filter(function(r) {
         if (r.isSelected) {
         console.log('HALLOOOO: ' , r);
         }
         })
         }, true);*/

        // TODO have to be implemented correctly for datepicking
        /*function getSupplierByDate(from, to) {
            var supplierByDate = $http.get('../..//all/between/:from/:to', {from: from, to: to})
                .then(function(response) {
                    console.log('getting data from server', response);
                    return supplierByDate;
                }, function(error) {
                    vm.error = error.data;
                })
        }*/

        /*function updateSelectedItems(row) {

            console.log('ITEMS SELECTED: ', vm.selectedItems);

            if (vm.selectedItems.length > 4) {
                vm.error = 'You can only select up to 4 suppliers to compare';
            } else {
                console.log('push item');
               vm.selectedItems.push(row);
            }
        }*/

        /*$scope.$watch('selectedItems', vm.updateSelectedItems, true);*/

    }

    function select() {
        return {
            require: '^stTable',
            template: '<input type="checkbox"/>',
            scope: {
                row: '=csSelect'
            },
            link: function (scope, element, attr, ctrl) {

                element.bind('change', function (evt) {
                    scope.$apply(function () {
                        ctrl.select(scope.row, 'multiple');
                    });
                });

                scope.$watch('row.isSelected', function (newValue, oldValue) {
                    if (newValue === true) {
                        element.parent().addClass('st-selected');
                    } else {
                        element.parent().removeClass('st-selected');
                    }
                });
            }
        };
    }

    function selectRow() {
        return {
            restrict: 'A',
            require: '^stTable',
            scope: {
                row: '=stSelectRow',
                callback: '&stSelected' // ADDED THIS
            },
            link: function (scope, element, attr, ctrl) {
                var mode = attr.stSelectMode || 'single';
                element.bind('click', function ($event) {
                    scope.$apply(function () {
                        ctrl.select(scope.row, mode, $event.shiftKey);
                        scope.callback(); // AND THIS
                    });
                });

                //***///
            }
        };
    }

})();