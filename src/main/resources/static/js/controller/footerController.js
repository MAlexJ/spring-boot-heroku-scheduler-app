'use strict';

app.controller('footerController', function (RestAPI, $scope) {
    let path = "/info";
    RestAPI.get(path)
        .then(function (response) {
            $scope.appInfo = response.data;
        }, function (reason) {
            $scope.error = reason.data
            alert(reason.data)
        });
});