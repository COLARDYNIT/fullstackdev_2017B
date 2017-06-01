(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('DeviceInStateDialogController', DeviceInStateDialogController);

    DeviceInStateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeviceInState', 'Device', 'Mood'];

    function DeviceInStateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeviceInState, Device, Mood) {
        var vm = this;

        vm.deviceInState = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query();
        vm.moods = Mood.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deviceInState.id !== null) {
                DeviceInState.update(vm.deviceInState, onSaveSuccess, onSaveError);
            } else {
                DeviceInState.save(vm.deviceInState, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fullstackdev2017BApp:deviceInStateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
