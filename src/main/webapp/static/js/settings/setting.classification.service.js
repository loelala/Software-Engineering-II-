
(function() {
    'use strict';

    angular.module('ClassificationFactoryModule', [])
        .factory('classificationService', classificationService);

    classificationService.$inject = ['$resource','$http'];

    function classificationService ($resource,$http) {
        var baseUri = "../../api/settings/";

        return {
            getClassification : getClassification,
            saveClassification : saveClassification

        }

        function saveClassification(classification){
            console.log("saveing classification " + classification);
            return $http.post(baseUri + "classification",classification);
        }

        function getClassification(){
            console.log("getting classification")
            return $resource(baseUri + "classification", {}, {
                query: {method:'GET'}
            });
        }
    }

})();