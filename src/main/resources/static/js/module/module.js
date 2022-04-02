'use strict';

let app = angular.module('app', ['ui.router', 'ngSanitize']);

app.config(function ($locationProvider, $stateProvider, $urlRouterProvider) {
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('');
    let states = [
        {
            name: 'monitor',
            url: '/',
            component: 'monitor'
        },
        {
            name: 'logs',
            url: '/logs',
            component: 'logs'
        },
        {
            name: 'settings',
            url: '/settings',
            component: 'settings'
        }
    ];

    states.forEach(function (state) {
        $stateProvider.state(state);
    });
    $urlRouterProvider.otherwise("/");
});