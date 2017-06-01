(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('device-in-state', {
            parent: 'entity',
            url: '/device-in-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DeviceInStates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-in-state/device-in-states.html',
                    controller: 'DeviceInStateController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('device-in-state-detail', {
            parent: 'device-in-state',
            url: '/device-in-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DeviceInState'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-in-state/device-in-state-detail.html',
                    controller: 'DeviceInStateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DeviceInState', function($stateParams, DeviceInState) {
                    return DeviceInState.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'device-in-state',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('device-in-state-detail.edit', {
            parent: 'device-in-state-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-in-state/device-in-state-dialog.html',
                    controller: 'DeviceInStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeviceInState', function(DeviceInState) {
                            return DeviceInState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-in-state.new', {
            parent: 'device-in-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-in-state/device-in-state-dialog.html',
                    controller: 'DeviceInStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                state: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('device-in-state', null, { reload: 'device-in-state' });
                }, function() {
                    $state.go('device-in-state');
                });
            }]
        })
        .state('device-in-state.edit', {
            parent: 'device-in-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-in-state/device-in-state-dialog.html',
                    controller: 'DeviceInStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeviceInState', function(DeviceInState) {
                            return DeviceInState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-in-state', null, { reload: 'device-in-state' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-in-state.delete', {
            parent: 'device-in-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-in-state/device-in-state-delete-dialog.html',
                    controller: 'DeviceInStateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceInState', function(DeviceInState) {
                            return DeviceInState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-in-state', null, { reload: 'device-in-state' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
