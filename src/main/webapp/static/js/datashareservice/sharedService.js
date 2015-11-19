(function() {
    'use strict';
    angular.module("sharedService",[])
        .service('shareService', function() {

            var service = {};

            var Admin = false;

            service.isAdmin = function(){
                return Admin;
            }

            service.updateIsAdmin = function(isAdminLocal) {
                console.log("updateIsAdmin to " + isAdminLocal);
                Admin = isAdminLocal;
            }


            return service;
        })



})();
