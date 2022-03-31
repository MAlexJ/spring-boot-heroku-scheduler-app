angular
    .module('app', [])
    .controller('AppController', function ($scope, $location, $http) {

        // API url
        let baseUrl = $location.absUrl().concat('rest/v1/events');

        // collection data
        $scope.taskArray = []

        // UUID id
        let id = generateUUID();

        // event types
        let event_type = "sse_event";
        let handshake_event_type = "handshake_event";

        // Event source
        let eventSource = new EventSource(baseUrl + `/subscribe/${id}/${event_type}`);

        eventSource.onopen = (event) => {
            console.warn("onopen")
            console.warn(event)
            console.warn("")
        }

        eventSource.onmessage = (event) => {
            console.log("onmessage")
            console.log(event)
            console.log("")
        };

        eventSource.onerror = (event) => {
            console.error("onerror");
            console.error(event)
            console.error("")
        };

        eventSource.addEventListener(event_type, event => {
            console.log(`>>> ${event_type} type`)
            console.log("id: " + event.lastEventId)
            console.log("data: " + event.data)
            $scope.$apply(function () {
                if ($scope.taskArray.length === 7) {
                    $scope.taskArray.shift();
                }
                $scope.taskArray.push(event);
            });
            console.log("")
        });

        eventSource.addEventListener(handshake_event_type, (event) => {
            console.log(handshake_event_type);
            console.log("lastEventId: " + event.lastEventId)

            console.log("data: " + event.data)
            console.log("JSON data: " + JSON.parse(event.data))

            $scope.$apply(function () {
                JSON.parse(event.data).forEach(message => {
                    let dto = mapEventToMessage(message);
                    $scope.taskArray.push(dto);
                })
            });

            console.log("")
        });

        function mapEventToMessage(message) {
            return {
                lastEventId: message.id, //
                data: message.message
            };
        }

        function generateUUID() {
            return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11) //
                .replace(/[018]/g, //
                    c => (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4) //
                        .toString(16));
        }
    });