'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $transitions, $interval, $location) {

        let tick = function () {
            $scope.clock = Date.now();
        }
        tick();
        $interval(tick, 1000);

        // API url
        let baseUrl = $location.absUrl().concat('rest/v1/events');

        // collection data
        $scope.taskArray = []

        // UUID id
        let id = generateUUID();

        // event types
        let event_type = "sse_event";
        let handshake_event_type = "handshake_event";

        // event source
        let eventSource;
        if (!eventSource) {
            eventSource = new EventSource(baseUrl + `/subscribe/${id}/${event_type}`);
        }

        $transitions.onSuccess({from: 'monitor'}, function (transition) {
            console.log("Left 'monitor' state and close 'eventSource' connection");
            if (eventSource) {
                eventSource.close();
            }
        });

        $transitions.onError({from: 'monitor'}, function (transition) {
            console.error("Error while leaving 'home' state: " + transition.error());
        });

        eventSource.onopen = (event) => {
            console.warn("onopen")
            console.warn(event)
        }

        eventSource.onmessage = (event) => {
            console.log("onmessage")
            console.log(event)
        };

        eventSource.onerror = (event) => {
            console.error("onerror");
            console.error(event)
        };

        eventSource.addEventListener(event_type, event => {
            console.log("id: " + event.lastEventId + `${event_type} type` + "data: " + event.data)
            $scope.$apply(function () {
                if ($scope.taskArray.length === 7) {
                    $scope.taskArray.shift();
                }
                $scope.taskArray.push(event);
            });
        });

        eventSource.addEventListener(handshake_event_type, (event) => {
            console.log(handshake_event_type + ", id: " + event.lastEventId + `${event_type} type`)
            console.log(handshake_event_type + ", data: " + JSON.parse(event.data))
            $scope.$apply(function () {
                JSON.parse(event.data).forEach(message => {
                    let dto = mapEventToMessage(message);
                    $scope.taskArray.push(dto);
                })
            });
        });

        function mapEventToMessage(message) {
            return {
                lastEventId: message.id, //
                data: message.message, //
                type: message.type ? message.type : message.event
            };
        }

        function generateUUID() {
            return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11) //
                .replace(/[018]/g, //
                    c => (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4) //
                        .toString(16));
        }
    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               <svg class="blink_1_second bd-placeholder-img rounded me-2" width="10" height="10" 
                  xmlns="http://www.w3.org/2000/svg" 
                  aria-hidden="true" 
                  preserveAspectRatio="xMidYMid slice" focusable="false">
                  <rect width="100%" height="100%" fill="cornflowerblue"></rect>
               </svg>
               Real time monitor 
               <br>
               {{ clock | date:'medium'}}
            </div>
            <div class="card-body">
               <h5 class="card-title">Scheduler task</h5>
               <div class="table-responsive">
                  <table class="table table-sm">
                     <thead>
                        <tr>
                           <th scope="col">#</th>
                           <th scope="col">Id</th>
                           <th scope="col">Message</th>
                           <th scope="col">Type</th>
                           <th scope="col">Time</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr ng-repeat="item in taskArray track by $index">
                           <th scope="row">{{item.lastEventId | convertTohHshCode}}</th>
                           <td>{{item.lastEventId}}</td>
                           <td>{{item.data}}</td>
                           <td>{{item.type}}</td>
                           <td>{{currentDate | date:'HH:mm:ss'}}</td>
                        </tr>
                     </tbody>
                  </table>
                  <div class="blink_3_second">Loading data 
                     <span class="blink_1_second">.</span>
                     <span class="blink_1_second">.</span>
                     <span class="blink_1_second">.</span>
                  </div>
               </div>
            </div>
            <div class="card-footer text-muted">
               <p></p>
            </div>
         </div>
      </div>
   </div>
</div>
`
});