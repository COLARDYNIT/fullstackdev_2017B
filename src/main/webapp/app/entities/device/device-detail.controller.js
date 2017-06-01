(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('DeviceDetailController', DeviceDetailController);

    DeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Device', 'Mood'];

    function DeviceDetailController($scope, $rootScope, $stateParams, previousState, entity, Device, Mood) {
        var vm = this;

        vm.device = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fullstackdev2017BApp:deviceUpdate', function(event, result) {
            vm.device = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
