(function() {
    'use strict';
    angular
        .module('bubbleBattStoreApp')
        .factory('Battery', Battery);

    Battery.$inject = ['$resource'];

    function Battery ($resource) {
        var resourceUrl =  'api/batteries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
