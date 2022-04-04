'use strict';

app.component('logs', {
    controller: function (RestAPI, $transitions, $scope) {

        // number of pagination buttons
        $scope.numberOfPaginationBtn = 1;

        // UI pagination start from 0 position
        $scope.activePaginationBtnNumber = 0;

        // REST api call
        RestAPI.get("/logs")
            .then(function (response) {
                let responseData = response.data;
                $scope.logs = responseData;
                if (responseData) {
                    let responseData = response.data;
                    $scope.logs = responseData;
                    $scope.numberOfPaginationBtn = responseData.totalPages;
                }
            }, function (reason) {
                $scope.error = reason.data
                alert(reason.data)
            });

        $scope.paginationRange = function () {
            let input = []
            for (let i = 1; i <= $scope.numberOfPaginationBtn; i++) {
                input.push(i)
            }
            return input;
        }

        $scope.getLogData = function (pageNumber) {
            // Pagination button position starts from 0
            $scope.activePaginationBtnNumber = pageNumber - 1;

            // REST api call
            RestAPI.get("/logs?page=" + $scope.activePaginationBtnNumber)
                .then(function (response) {
                    let responseData = response.data;
                    $scope.logs = responseData;
                    $scope.numberOfPaginationBtn = responseData.totalPages;
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        $scope.calculateActivePage = function (pageNumber) {
            return pageNumber - 1;
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
                     ng-repeat="pageNumber in paginationRange()" 
                     style="cursor: pointer"
                     ng-class="{active : activePaginationBtnNumber === calculateActivePage(pageNumber)}">
                     <a id="page-number-id-{{ pageNumber - 1}}"
                        ng-click="getLogData(pageNumber)"
                        class="page-link">
                     {{ pageNumber }}
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
