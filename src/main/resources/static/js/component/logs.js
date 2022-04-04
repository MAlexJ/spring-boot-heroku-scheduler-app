'use strict';

app.component('logs', {
    controller: function (RestAPI, $transitions, $scope) {

        // total number of events in logs
        $scope.totalEvents = -1;

        // UI pagination start from 0 position
        $scope.activePaginationPosition = 0;

        // number of pagination buttons by default
        $scope.defaultNumberOfPagination = 3;

        $scope.pagination = [];

        function initDefaultPagination() {
            let input = []

            if ($scope.totalEvents === 0) {
                input.push(0)
                return input;
            }

            if ($scope.totalEvents <= $scope.defaultNumberOfPagination) {
                for (let i = 0; i < $scope.totalEvents; i++) {
                    input.push(i)
                }
                return input;
            }

            for (let i = 0; i < $scope.defaultNumberOfPagination; i++) {
                input.push(i)
            }
            return input;
        }

        // REST api call
        RestAPI.get("/logs")
            .then(function (response) {
                let responseData = response.data;
                $scope.logs = responseData;
                if (responseData) {
                    let responseData = response.data;
                    $scope.logs = responseData;
                    $scope.totalEvents = responseData.totalPages - 1;
                    $scope.pagination = initDefaultPagination();
                }
            }, function (reason) {
                $scope.error = reason.data
                alert(reason.data)
            });

        $scope.paginationRange = function () {
            let input = []
            // total events are less than the default number of pagination
            if ($scope.totalEvents <= $scope.defaultNumberOfPagination) {
                for (let i = 0; i <= $scope.totalEvents; i++) {
                    input.push(i)
                }
                return input;
            }

            for (let i = 1; i <= $scope.numberOfPaginationBtn; i++) {
                input.push(i)
            }
            return input;
        }

        $scope.getLogData = function (pageNumber) {
            // Pagination button position starts from 0
            $scope.activePaginationPosition = pageNumber;

            // REST api call
            RestAPI.get("/logs?page=" + pageNumber)
                .then(function (response) {
                    let responseData = response.data;
                    $scope.logs = responseData;
                    $scope.totalEvents = responseData.totalPages - 1;
                    $scope.pagination = calculatePagination(pageNumber);
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        function calculatePagination(page) {
            let tempArr = [];
            let temp = page;

            if (page === 0) {
                for (let i = 0; i < $scope.defaultNumberOfPagination; i++) {
                    tempArr[i] = temp++;
                }
                return tempArr;
            }

            if (page === $scope.totalEvents) {
                for (let i = $scope.defaultNumberOfPagination; i > 0; i--) {
                    if(temp < 0) {
                        break;
                    }
                    tempArr[i - 1] = temp--;
                }
                return tempArr;
            }

            temp = page - 1;
            for (let i = 0; i < $scope.defaultNumberOfPagination; i++) {
                tempArr[i] = temp++;
            }

            return tempArr;
        }

        $scope.calculateActivePage = function (pageNumber) {
            return pageNumber;
        }

    }, template: `
<div class="container">
   <div class="row justify-content-md-center" style="display: block">
      <div class="row" style="padding: 40px;">
         <div class="col-lg-12"></div>
         <div class="col-lg-12 table-responsive">
            <table class="table">
               <thead class="table-dark">
                  <tr>
                     <th scope="col">#</th>
                     <th scope="col">Event</th>
                     <th scope="col">Message</th>
                     <th scope="col">Scheduler</th>
                     <th scope="col">Date</th>
                  </tr>
               </thead>
               <tbody>
                  <tr ng-repeat="log in logs.content track by $index" >
                     <th scope="row">{{log.id}}</th>
                     <td>{{log.event}}</td>
                     <td>{{log.message}}</td>
                     <td>{{log.scheduler}}</td>
                     <td>{{log.created}}</td>
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="col-lg-12" style="padding-top: 10px">
            <nav aria-label="Page navigation example">
               <ul class="pagination justify-content-center">
                  <li class="page-item disabled">
                     <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                  </li>
                  <li class="page-item" 
                     ng-repeat="pageNumber in pagination" 
                     style="cursor: pointer"
                     ng-class="{ active : pageNumber == activePaginationPosition}">
                     <a id="page-number-id-{{ pageNumber }}"
                        ng-click="getLogData(pageNumber)"
                        class="page-link">
                     {{ pageNumber }}
                     </a>
                  </li>
                  <li class="page-item" style="cursor: default">
                     <a class="page-link">
                     ... 
                     </a>
                  </li>
                  <li class="page-item"                  
                     style="cursor: pointer"
                     ng-class="{ active : totalEvents == activePaginationPosition}">
                     <a id="page-number-id-{{ totalEvents }}"
                        ng-click="getLogData(totalEvents)"
                        class="page-link">
                     {{ totalEvents }}
                     </a>
                  </li>
                  <li class="page-item">
                     <a class="page-link" href="#">Next</a>
                  </li>
               </ul>
            </nav>
         </div>
      </div>
   </div>
</div>
`
});
