(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BatteryController', BatteryController);

    BatteryController.$inject = ['DataUtils', 'Battery'];

    function BatteryController(DataUtils, Battery) {

        var vm = this;

        vm.batteries = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Battery.query(function(result) {
                vm.batteries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
