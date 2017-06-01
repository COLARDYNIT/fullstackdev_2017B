(function() {
    'use strict';

    angular
        .module('fullstackdev2017BApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mood', {
            parent: 'entity',
            url: '/mood?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Moods'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mood/moods.html',
                    controller: 'MoodController',
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
        .state('mood-detail', {
            parent: 'mood',
            url: '/mood/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mood'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mood/mood-detail.html',
                    controller: 'MoodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Mood', function($stateParams, Mood) {
                    return Mood.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mood',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mood-detail.edit', {
            parent: 'mood-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mood/mood-dialog.html',
                    controller: 'MoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mood', function(Mood) {
                            return Mood.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mood.new', {
            parent: 'mood',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mood/mood-dialog.html',
                    controller: 'MoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mood', null, { reload: 'mood' });
                }, function() {
                    $state.go('mood');
                });
            }]
        })
        .state('mood.edit', {
            parent: 'mood',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mood/mood-dialog.html',
                    controller: 'MoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mood', function(Mood) {
                            return Mood.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mood', null, { reload: 'mood' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mood.delete', {
            parent: 'mood',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mood/mood-delete-dialog.html',
                    controller: 'MoodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mood', function(Mood) {
                            return Mood.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mood', null, { reload: 'mood' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
