(function() {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'BatteryController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
