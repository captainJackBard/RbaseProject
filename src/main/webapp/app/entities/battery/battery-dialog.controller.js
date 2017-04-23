(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BatteryDialogController', BatteryDialogController);

    BatteryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Battery', 'Stock'];

    function BatteryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Battery, Stock) {
        var vm = this;

        vm.battery = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.stocks = Stock.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.battery.id !== null) {
                Battery.update(vm.battery, onSaveSuccess, onSaveError);
            } else {
                Battery.save(vm.battery, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bubbleBattStoreApp:batteryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, battery) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        battery.image = base64Data;
                        battery.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
