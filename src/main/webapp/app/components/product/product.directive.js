'use strict';

angular
    .module('bubbleBattStoreApp')
    .directive('productCard', [function () {
    return {
        restrict: 'E',
        scope: {
            battery : "="
        },
        templateUrl: 'app/components/product/product.html',
        controller: ['$scope', function ($scope) {
            console.log($scope);
        }]
    };

}]);
