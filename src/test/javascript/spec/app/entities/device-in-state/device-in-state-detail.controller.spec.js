'use strict';

describe('Controller Tests', function() {

    describe('DeviceInState Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDeviceInState, MockDevice, MockMood;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDeviceInState = jasmine.createSpy('MockDeviceInState');
            MockDevice = jasmine.createSpy('MockDevice');
            MockMood = jasmine.createSpy('MockMood');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DeviceInState': MockDeviceInState,
                'Device': MockDevice,
                'Mood': MockMood
            };
            createController = function() {
                $injector.get('$controller')("DeviceInStateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fullstackdev2017BApp:deviceInStateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
