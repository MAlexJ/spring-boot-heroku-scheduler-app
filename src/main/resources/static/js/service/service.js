'use strict';

app.factory('RestAPI', ['$http', '$location', function ($http, $location) {
    let Response = {};
    let baseUrl = $location.absUrl().concat('rest/v1');

    Response.get = function (url) {
        return $http.get(baseUrl + url);
    };

    Response.post = function (url, inData) {
        return $http.post(baseUrl + url, inData);
    };

    return Response;
}]);