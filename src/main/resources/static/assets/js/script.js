angular
    .module('app', [])
    .controller('AppController', function ($scope, $location, $http) {
        let baseUrl = getBaseUrl();
        $scope.taskArray = []
        initOnTaskState();
        initEventSource();
        $scope.handleTask1 = function () {
            if ($scope.labelTaskName1 === 'ON') {
                interruptTask();
                return;
            }
            runTask();
        };

        function getBaseUrl() {
            return $location.absUrl().concat('rest/v1/scheduler');
        }

        function initEventSource() {
            let eventSource = new EventSource(baseUrl + '/emit/id1/FixedRateSchedulerTask');
            eventSource.onmessage = function (event) {
                $scope.$apply(function () {
                    if ($scope.taskArray.length === 7) {
                        $scope.taskArray.shift();
                    }
                    $scope.taskArray.push(event.data);
                });
            };
        }

        function initOffTaskState() {
            $scope.colorTask1 = {'background-color': 'black'};
            $scope.labelTaskName1 = 'OFF';
        }

        function interruptTask() {
            $http.get(baseUrl + "/interrupt")
                .then(function (response) {
                    initOffTaskState();
                    console.log(response.data)
                }, function (reason) {
                    console.log("Error:" + reason.data)
                });
        }

        function runTask() {
            $http.get(baseUrl + "/run")
                .then(function (response) {
                    initOnTaskState();
                    console.log(response.data)
                }, function (reason) {
                    console.log("Error:" + reason.data)
                });
        }

        function initOnTaskState() {
            $scope.colorTask1 = {'background-color': '#0b5ed7'};
            $scope.labelTaskName1 = 'ON';
        }
    });