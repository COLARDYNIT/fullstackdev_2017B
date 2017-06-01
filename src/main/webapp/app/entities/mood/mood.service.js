(function() {
    'use strict';
    angular
        .module('fullstackdev2017BApp')
        .factory('Mood', Mood);

    Mood.$inject = ['$resource'];

    function Mood ($resource) {
        var resourceUrl =  'api/moods/:id';

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
