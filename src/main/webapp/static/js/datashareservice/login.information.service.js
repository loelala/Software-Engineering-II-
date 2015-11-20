/**
 * The loginInformationHolderService stores all necessary login information in the ram of the application
 * Information holt:
 *  - is the user logged in?
 *  - is the user a admin user?
 */
(function() {
    'use strict';
    angular.module("loginInformationHolderModule",[])
        .service('loginInformationHolderService', function() {

            var loginInformationHolder = {};
            loginInformationHolder.admin = false;
            loginInformationHolder.isLoggedIn = false;

            return {
                isAdmin :  function(){
                    //console.log("returning admin "+ loginInformationHolder.admin);
                    return loginInformationHolder.admin;
                },
                isLoggedIn :  function(){
                    //console.log("returning admin "+ loginInformationHolder.isLoggedIn);
                    return loginInformationHolder.isLoggedIn;
                },

                updateIsAdmin : function(isAdminLocal) {
                    //console.log("updateIsAdmin to " + isAdminLocal);
                    loginInformationHolder.admin = isAdminLocal;
                    //console.log("updateIsAdmin to (After)" + loginInformationHolder.admin);
                },
                updateIsLoggedIn : function(isLoggedIn) {
                    //console.log("updateIsAdmin to " + isLoggedIn);
                    loginInformationHolder.isLoggedIn = isLoggedIn;
                    //console.log("updateIsAdmin to (After)" + loginInformationHolder.isLoggedIn);
                }
            };
        })



})();
