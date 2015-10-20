angular.module('SupplierManagement.controllers', ['smart-table']).

controller('IndexCtrl', function () {
    var vm = this; 
    
    vm.test = 'hallo ich bin ein Test'; 
}).

controller('SupplierCtrl', function () {
    var vm = this; 
    vm.supplierList = [
      {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}},
        {name: 'Test GmbH', address: {city: 'München', street: 'Lothstrasse'}}
    ];
}).

controller('DatePickerCtrl', function () {
    var vm = this; 
    
    vm.test = 'Test Test'; 
    
    vm.dateFrom; 
    vm.dateUntil; 
    
    vm.today = function() {
        vm.dateFrom = new Date();
    };
    vm.today();

    vm.clear = function () {
        vm.dt = null;
    };

  // Disable weekend selection
    vm.disabled = function(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    vm.toggleMin = function() {
        vm.minDate = vm.minDate ? null : new Date();
    };
    vm.toggleMin();
  
    vm.maxDate = new Date(vm.dateFrom);

    vm.open = function($event) {
        vm.status.opened = true;
    };

    vm.setDate = function(year, month, day) {
        vm.dt = new Date(year, month, day);
    };

    vm.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    vm.format = vm.formats[0];

    vm.status = {
        opened: false
  };

  var tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  var afterTomorrow = new Date();
  afterTomorrow.setDate(tomorrow.getDate() + 2);
  vm.events =
    [
      {
        date: tomorrow,
        status: 'full'
      },
      {
        date: afterTomorrow,
        status: 'partially'
      }
    ];

  vm.getDayClass = function(date, mode) {
    if (mode === 'day') {
      var dayToCheck = new Date(date).setHours(0,0,0,0);

      for (var i=0;i<$scope.events.length;i++){
        var currentDay = new Date(vm.events[i].date).setHours(0,0,0,0);

        if (dayToCheck === currentDay) {
          return vm.events[i].status;
        }
      }
    }

    return '';
  };
    
}); 