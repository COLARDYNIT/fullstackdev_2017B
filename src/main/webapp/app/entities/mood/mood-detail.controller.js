(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .controller('MoodDetailController', MoodDetailController);

    MoodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mood', 'Device'];

    function MoodDetailController($scope, $rootScope, $stateParams, previousState, entity, Mood, Device) {
        var vm = this;

        vm.mood = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fullstackdev2017BApp:moodUpdate', function(event, result) {
            vm.mood = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
