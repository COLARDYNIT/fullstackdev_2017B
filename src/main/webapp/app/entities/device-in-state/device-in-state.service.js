(function() {
    'use strict';
    angular
        .module('fullstackdev2017BApp')
        .factory('DeviceInState', DeviceInState);

    DeviceInState.$inject = ['$resource'];

    function DeviceInState ($resource) {
        var resourceUrl =  'api/device-in-states/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
