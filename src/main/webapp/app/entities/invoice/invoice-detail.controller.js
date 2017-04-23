(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('InvoiceDetailController', InvoiceDetailController);

    InvoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Invoice', 'Battery', 'Branch'];

    function InvoiceDetailController($scope, $rootScope, $stateParams, previousState, entity, Invoice, Battery, Branch) {
        var vm = this;

        vm.invoice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bubbleBattStoreApp:invoiceUpdate', function(event, result) {
            vm.invoice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
