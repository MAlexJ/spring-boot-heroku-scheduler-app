'use strict';

app.component('logs', {
    controller: function (RestAPI, $transitions, $scope) {

        // total number of events in logs
        $scope.totalEvents = 0;

        // UI pagination start from 0 position
        $scope.activePaginationPosition = 0;

        // number of pagination buttons by default
        $scope.defaultNumberOfPagination = 7;

        $scope.pagination = [];

        // REST api call
        restApiGetLogs()

        $scope.getLogData = function (pageNumber) {
            // Pagination button position starts from 0
            $scope.activePaginationPosition = pageNumber;
            // REST api call
            restApiGetLogs(pageNumber);
        }

        function restApiGetLogs(pageNumber) {
            let path = "/logs";
            if (pageNumber) {
                path = path + "?page=" + pageNumber;
            }
            RestAPI.get(path)
                .then(function (response) {
                    let responseData = response.data;
                    $scope.logs = responseData;
                    if (responseData) {
                        let responseData = response.data;
                        $scope.logs = responseData;
                        $scope.totalEvents = responseData.totalPages - 1;
                        if (pageNumber) {
                            $scope.pagination = calculatePagination(pageNumber);
                        } else {
                            $scope.pagination = initDefaultPagination();
                        }
                    }
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        // init state of pagination
        function initDefaultPagination() {
            let input = []
            if ($scope.totalEvents === 0) {
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
                    if (temp < 0) {
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

        $scope.calculateActivePage = function (pageNumber) {
            return pageNumber;
        }

    }, template: `
<div class="container">
   <div class="row justify-content-md-center" style="display: block">
      <div class="row" style="padding-top: 40px;padding-bottom: 20px;margin-right: 0px;padding-right: 0px;">
         <ul class="nav nav-tabs" style="cursor: pointer">
            <li class="nav-item">
               <a class="nav-link active" aria-current="page" style="color: black;">Events</a>
            </li>
            <li class="nav-item">
               <a class="nav-link" style="color: black;">Errors</a>
            </li>
         </ul>
      </div>
      <div class="row" style="padding-bottom: 40px;margin-right: 0px;padding-right: 0px;">
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
         <div class="col-lg-12" style="padding-bottom: 60px;position: fixed;right: 0;left: 0;bottom: 0;"></div>
         <div class="col-lg-12">
            <nav aria-label="Page navigation example">
               <ul class="pagination justify-content-center">
                  <li class="page-item disabled">
                     <a class="page-link" href="#" tabindex="-1" aria-disabled="true"> < </a>
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
                  <li class="page-item">
                     <a class="page-link" href="#"> > </a>
                  </li>
               </ul>
            </nav>
         </div>
      </div>
   </div>
</div>
`
});
