(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('StockDialogController', StockDialogController);

    StockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stock', 'Battery', 'Branch'];

    function StockDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Stock, Battery, Branch) {
        var vm = this;

        vm.stock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.batteries = Battery.query({filter: 'stock-is-null'});
        $q.all([vm.stock.$promise, vm.batteries.$promise]).then(function() {
            if (!vm.stock.battery || !vm.stock.battery.id) {
                return $q.reject();
            }
            return Battery.get({id : vm.stock.battery.id}).$promise;
        }).then(function(battery) {
            vm.batteries.push(battery);
        });
        vm.branches = Branch.query();

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
