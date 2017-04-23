(function() {
    'use strict';
    angular
        .module('bubbleBattStoreApp')
        .factory('Stock', Stock);

    Stock.$inject = ['$resource', 'DateUtils'];

    function Stock ($resource, DateUtils) {
        var resourceUrl =  'api/stocks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.stockDate = DateUtils.convertLocalDateFromServer(data.stockDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.stockDate = DateUtils.convertLocalDateToServer(copy.stockDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.stockDate = DateUtils.convertLocalDateToServer(copy.stockDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
