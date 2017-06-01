(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('DeviceInStateDetailController', DeviceInStateDetailController);

    DeviceInStateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DeviceInState', 'Device', 'Mood'];

    function DeviceInStateDetailController($scope, $rootScope, $stateParams, previousState, entity, DeviceInState, Device, Mood) {
        var vm = this;

        vm.deviceInState = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fullstackdev2017BApp:deviceInStateUpdate', function(event, result) {
            vm.deviceInState = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
