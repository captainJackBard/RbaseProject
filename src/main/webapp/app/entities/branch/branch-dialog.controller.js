(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BranchDialogController', BranchDialogController);

    BranchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Branch', 'Stock', 'User', 'Invoice'];

    function BranchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Branch, Stock, User, Invoice) {
        var vm = this;

        vm.branch = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stocks = Stock.query();
        vm.users = User.query();
        vm.invoices = Invoice.query();
        vm.googleMapKey = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDqijN9STZnNuCr0mu8gWCcO4aLOkxR_rg';
        vm.gmapKey = {key: 'AIzaSyDqijN9STZnNuCr0mu8gWCcO4aLOkxR_rg'};

        vm.position = [15.1449853, 120.570361];

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                console.log(position);
                vm.branch.position = [position.coords.latitude, position.coords.longitude];
            });
        }

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

        vm.markerDrag = function (event) {
            console.log(event);
            vm.branch.latitude = event.latLng.lat();
            vm.branch.longitude = event.latLng.lng();
        };


    }
})();
