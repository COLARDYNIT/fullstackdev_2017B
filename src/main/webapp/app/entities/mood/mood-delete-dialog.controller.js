(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('MoodDeleteController',MoodDeleteController);

    MoodDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mood'];

    function MoodDeleteController($uibModalInstance, entity, Mood) {
        var vm = this;

        vm.mood = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mood.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
