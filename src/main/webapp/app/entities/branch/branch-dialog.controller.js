(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BranchDialogController', BranchDialogController);

    BranchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Branch', 'Stock', 'User'];

    function BranchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Branch, Stock, User) {
        var vm = this;

        vm.branch = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stocks = Stock.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.branch.id !== null) {
                Branch.update(vm.branch, onSaveSuccess, onSaveError);
            } else {
                Branch.save(vm.branch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bubbleBattStoreApp:branchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
