'use strict';

describe('Controller Tests', function() {

    describe('Branch Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBranch, MockStock, MockUser, MockInvoice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBranch = jasmine.createSpy('MockBranch');
            MockStock = jasmine.createSpy('MockStock');
            MockUser = jasmine.createSpy('MockUser');
            MockInvoice = jasmine.createSpy('MockInvoice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Branch': MockBranch,
                'Stock': MockStock,
                'User': MockUser,
                'Invoice': MockInvoice
            };
            createController = function() {
                $injector.get('$controller')("BranchDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bubbleBattStoreApp:branchUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
