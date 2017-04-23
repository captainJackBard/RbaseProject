(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('StockDetailController', StockDetailController);

    StockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stock', 'Battery', 'Branch'];

    function StockDetailController($scope, $rootScope, $stateParams, previousState, entity, Stock, Battery, Branch) {
        var vm = this;

        vm.stock = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bubbleBattStoreApp:stockUpdate', function(event, result) {
            vm.stock = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
