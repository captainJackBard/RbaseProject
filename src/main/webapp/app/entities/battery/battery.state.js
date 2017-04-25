(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('battery', {
            parent: 'entity',
            url: '/batteries',
            data: {
                authorities: [],
                pageTitle: 'Batteries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/battery/batteries.html',
                    controller: 'BatteryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('battery-detail', {
            parent: 'battery',
            url: '/battery/{id}',
            data: {
                authorities: [],
                pageTitle: 'Battery'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/battery/battery-detail.html',
                    controller: 'BatteryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Battery', function($stateParams, Battery) {
                    return Battery.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'battery',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('battery-detail.edit', {
            parent: 'battery-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/battery/battery-dialog.html',
                    controller: 'BatteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Battery', function(Battery) {
                            return Battery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('battery.new', {
            parent: 'battery',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/battery/battery-dialog.html',
                    controller: 'BatteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                model: null,
                                price: null,
                                name: null,
                                image: null,
                                imageContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('battery', null, { reload: 'battery' });
                }, function() {
                    $state.go('battery');
                });
            }]
        })
        .state('battery.edit', {
            parent: 'battery',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/battery/battery-dialog.html',
                    controller: 'BatteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Battery', function(Battery) {
                            return Battery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('battery', null, { reload: 'battery' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('battery.delete', {
            parent: 'battery',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/battery/battery-delete-dialog.html',
                    controller: 'BatteryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Battery', function(Battery) {
                            return Battery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('battery', null, { reload: 'battery' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
