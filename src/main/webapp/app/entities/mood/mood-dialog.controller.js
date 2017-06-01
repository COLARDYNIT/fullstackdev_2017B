(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('MoodDialogController', MoodDialogController);

    MoodDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mood', 'Device'];

    function MoodDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mood, Device) {
        var vm = this;

        vm.mood = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mood.id !== null) {
                Mood.update(vm.mood, onSaveSuccess, onSaveError);
            } else {
                Mood.save(vm.mood, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fullstackdev2017BApp:moodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
