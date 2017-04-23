(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('StockDialogController', StockDialogController);

    StockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stock', 'Branch', 'Battery'];

    function StockDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stock, Branch, Battery) {
        var vm = this;

        vm.stock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.branches = Branch.query();
        vm.batteries = Battery.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stock.id !== null) {
                Stock.update(vm.stock, onSaveSuccess, onSaveError);
            } else {
                Stock.save(vm.stock, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bubbleBattStoreApp:stockUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.stockDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
