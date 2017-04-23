(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BatteryDetailController', BatteryDetailController);

    BatteryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Battery', 'Stock'];

    function BatteryDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Battery, Stock) {
        var vm = this;

        vm.battery = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bubbleBattStoreApp:batteryUpdate', function(event, result) {
            vm.battery = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
