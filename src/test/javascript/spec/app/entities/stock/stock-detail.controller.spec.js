'use strict';

describe('Controller Tests', function() {

    describe('Stock Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStock, MockBranch, MockBattery;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStock = jasmine.createSpy('MockStock');
            MockBranch = jasmine.createSpy('MockBranch');
            MockBattery = jasmine.createSpy('MockBattery');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stock': MockStock,
                'Branch': MockBranch,
                'Battery': MockBattery
            };
            createController = function() {
                $injector.get('$controller')("StockDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bubbleBattStoreApp:stockUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
