(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BatteryDeleteController',BatteryDeleteController);

    BatteryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Battery'];

    function BatteryDeleteController($uibModalInstance, entity, Battery) {
        var vm = this;

        vm.battery = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Battery.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
