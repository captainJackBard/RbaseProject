(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .controller('BranchDetailController', BranchDetailController);

    BranchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Branch', 'Stock', 'User'];

    function BranchDetailController($scope, $rootScope, $stateParams, previousState, entity, Branch, Stock, User) {
        var vm = this;

        vm.branch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bubbleBattStoreApp:branchUpdate', function(event, result) {
            vm.branch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
