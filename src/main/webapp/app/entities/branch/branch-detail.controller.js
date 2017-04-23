(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BranchDetailController', BranchDetailController);

    BranchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Branch', 'Stock', 'User', 'Invoice'];

    function BranchDetailController($scope, $rootScope, $stateParams, previousState, entity, Branch, Stock, User, Invoice) {
        var vm = this;

        vm.branch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bubbleBattStoreApp:branchUpdate', function(event, result) {
            vm.branch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
