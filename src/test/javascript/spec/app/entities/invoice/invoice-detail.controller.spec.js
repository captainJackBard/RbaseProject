'use strict';

describe('Controller Tests', function() {

    describe('Invoice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvoice, MockLocation, MockBattery, MockBranch;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvoice = jasmine.createSpy('MockInvoice');
            MockLocation = jasmine.createSpy('MockLocation');
            MockBattery = jasmine.createSpy('MockBattery');
            MockBranch = jasmine.createSpy('MockBranch');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Invoice': MockInvoice,
                'Location': MockLocation,
                'Battery': MockBattery,
                'Branch': MockBranch
            };
            createController = function() {
                $injector.get('$controller')("InvoiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bubbleBattStoreApp:invoiceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
