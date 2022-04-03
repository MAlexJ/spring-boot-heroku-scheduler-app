'use strict';

app.component('logs', {
    controller: function (RestAPI, $transitions, $scope) {

        RestAPI.get("/logs")
            .then(function (response) {
                $scope.logs = response.data;
            }, function (reason) {
                $scope.error = reason.data
                alert(reason.data)
            });

    }, template: `
<div class="container">
   <div class="row justify-content-md-center" style="display: block">
      <div class="row" style="padding: 40px;">
         <div class="col-lg-12"></div>
         <div class="col-lg-12">
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
                  <tr ng-repeat="log in logs.modelEventList" >
                     <th scope="row">{{log.id}}</th>
                     <td>{{log.event}}</td>
                     <td>{{log.message}}</td>
                     <td>{{log.scheduler}}</td>
                     <td>{{log.created}}</td>
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="col-lg-12">
            <nav aria-label="Page navigation example">
               <ul class="pagination justify-content-center">
                  <li class="page-item disabled">
                     <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                  </li>
                  <li class="page-item"><a class="page-link" href="#">1</a></li>
                  <li class="page-item active"><a class="page-link" href="#">2</a></li>
                  <li class="page-item"><a class="page-link" href="#">3</a></li>
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
