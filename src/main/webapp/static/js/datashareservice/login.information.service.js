/**
 * The loginInformationHolderService stores all necessary login information in the ram of the application
 * Information holt:
 *  - is the user logged in?
 *  - is the user a admin user?
 *  - the actual user
 */
(function() {
    'use strict';
    angular.module("loginInformationHolderModule",[])
        .service('loginInformationHolderService', function() {

            var loginInformationHolder = {};
            loginInformationHolder.admin = false;
            loginInformationHolder.isLoggedIn = false;
            loginInformationHolder.actualUser = '';

            return {
                isAdmin :  function(){
                    return loginInformationHolder.admin;
                },
                isLoggedIn :  function(){
                    return loginInformationHolder.isLoggedIn;
                },
                actualUser : function() {
                    return loginInformationHolder.actualUser;
                },

                updateIsAdmin : function(isAdminLocal) {
                    loginInformationHolder.admin = isAdminLocal;
                },
                updateIsLoggedIn : function(isLoggedIn) {
                    loginInformationHolder.isLoggedIn = isLoggedIn;
                },
                updateActualUser : function(user) {
                    loginInformationHolder.actualUser = user;
                }
            };
        })



})();
