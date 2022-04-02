'use strict';

app.controller('navBarController', function ($state, $scope, $window) {
    $scope.hidingNavBar = function () {
        if ($window.innerWidth <= 990) {
            $('#btn-menu-toggle').click();
        }
    }
});