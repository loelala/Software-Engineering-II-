/**
 * Created by B3rni on 10.12.2015.
 */
(function() {
    'use strict';

    angular.module('ScoringFactoryModule', [])
        .factory('scoringService', scoringService);

    scoringService.$inject = ['$resource','$http'];

    function scoringService ($resource,$http) {
        var baseUri = "../../api/settings/";

        return  {
            getScoring : getScore,
            saveScoring:saveScoring
        }

        function saveScoring(scoringLimits){
            console.log("saveing scoring " + scoringLimits);
            return $http.post(baseUri + "scoring",scoringLimits);
        }

        function getScore(){
            return $resource(baseUri + "scoring", {}, {
                query: {method:'GET'}
            });
        }
    }

})();