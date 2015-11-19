(function() {
    'use strict';
    angular.module("sharedService",[])
        .service('shareService', function() {

            var sharedService = {};

            sharedService.list = [];

            sharedService.getListItems = function() {
                return sharedService.list;
            };

            sharedService.addItems = function(item) {
                if(sharedService.list.length >= 4) {
                    console.log('not adding item, cant have more than 4');
                    return false;
                }

                console.log('adding a new item');
                var i = 0;
                var index = -1;
                for(i = 0 ; i < sharedService.list.length ; i++)
                {
                    console.log(sharedService.list[i]);
                    if(item["id"] == sharedService.list[i]["id"])
                    {
                        index = i;
                    }

                }
                if(index == -1)
                {
                    sharedService.list.push(item);
                    return true;
                }
                else
                {
                    return false;
                }

            };

            return sharedService;
        })



})();
