(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('DeviceInStateDeleteController',DeviceInStateDeleteController);

    DeviceInStateDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeviceInState'];

    function DeviceInStateDeleteController($uibModalInstance, entity, DeviceInState) {
        var vm = this;

        vm.deviceInState = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeviceInState.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
