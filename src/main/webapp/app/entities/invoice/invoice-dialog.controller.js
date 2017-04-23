(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('InvoiceDialogController', InvoiceDialogController);

    InvoiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Invoice', 'Location', 'Battery', 'Branch'];

    function InvoiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Invoice, Location, Battery, Branch) {
        var vm = this;

        vm.invoice = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query({filter: 'invoice-is-null'});
        $q.all([vm.invoice.$promise, vm.locations.$promise]).then(function() {
            if (!vm.invoice.location || !vm.invoice.location.id) {
                return $q.reject();
            }
            return Location.get({id : vm.invoice.location.id}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.batteries = Battery.query();
        vm.branches = Branch.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.invoice.id !== null) {
                Invoice.update(vm.invoice, onSaveSuccess, onSaveError);
            } else {
                Invoice.save(vm.invoice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bubbleBattStoreApp:invoiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
