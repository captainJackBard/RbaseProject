'use strict';

angular.module('bubbleBattStoreApp')
    .factory('BootSwatchService', ['$http',function ($http) {
        return {
            get: function () {
                console.log('something servoice');
                return $http.get('http://bootswatch.com/api/3.json').then(function (response) {
                    return response.data.themes;
                });
            }
        };
    }]);
