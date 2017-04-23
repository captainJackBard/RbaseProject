(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('branch', {
            parent: 'entity',
            url: '/branch',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Branches'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/branch/branches.html',
                    controller: 'BranchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('branch-detail', {
            parent: 'branch',
            url: '/branch/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Branch'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/branch/branch-detail.html',
                    controller: 'BranchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Branch', function($stateParams, Branch) {
                    return Branch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'branch',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('branch-detail.edit', {
            parent: 'branch-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('branch.new', {
            parent: 'branch',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                latitude: null,
                                longitude: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('branch');
                });
            }]
        })
        .state('branch.edit', {
            parent: 'branch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('branch.delete', {
            parent: 'branch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch/branch-delete-dialog.html',
                    controller: 'BranchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
