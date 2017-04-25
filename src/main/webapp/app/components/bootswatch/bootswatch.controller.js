'use strict';

angular.module('bubbleBattStoreApp')
    .controller('BootSwatchController', ['$scope', 'BootSwatchService', function ($scope, BootSwatchService) {
        console.log('Something Controller');
        /*Get the list of availabel bootswatch themes*/
        BootSwatchService.get().then(function(themes) {
            $scope.themes = themes;
            $scope.themes.unshift({name:'Default',css:''});
        });
    }]);
