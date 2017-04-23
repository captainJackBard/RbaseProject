(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('invoice', {
            parent: 'entity',
            url: '/invoice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Invoices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invoice/invoices.html',
                    controller: 'InvoiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('invoice-detail', {
            parent: 'invoice',
            url: '/invoice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Invoice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invoice/invoice-detail.html',
                    controller: 'InvoiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Invoice', function($stateParams, Invoice) {
                    return Invoice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'invoice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('invoice-detail.edit', {
            parent: 'invoice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice/invoice-dialog.html',
                    controller: 'InvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Invoice', function(Invoice) {
                            return Invoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invoice.new', {
            parent: 'invoice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice/invoice-dialog.html',
                    controller: 'InvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                total: null,
                                paid: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('invoice', null, { reload: 'invoice' });
                }, function() {
                    $state.go('invoice');
                });
            }]
        })
        .state('invoice.edit', {
            parent: 'invoice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice/invoice-dialog.html',
                    controller: 'InvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Invoice', function(Invoice) {
                            return Invoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invoice', null, { reload: 'invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invoice.delete', {
            parent: 'invoice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invoice/invoice-delete-dialog.html',
                    controller: 'InvoiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Invoice', function(Invoice) {
                            return Invoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invoice', null, { reload: 'invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
