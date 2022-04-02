'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $timeout) {

    }, template: `
<div class="container">
   <div class="row" style="padding: 40px;">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               Featured
            </div>
            <div class="card-body">
               <h5 class="card-title">Special title treatment</h5>
               <ul class="list-group">
                  <li class="list-group-item d-flex justify-content-between align-items-center">
                     A list item
                     <span class="badge bg-primary rounded-pill">14</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between align-items-center">
                     A second list item
                     <span class="badge bg-primary rounded-pill">2</span>
                  </li>
                  <li class="list-group-item d-flex justify-content-between align-items-center">
                     A third list item
                     <span class="badge bg-primary rounded-pill">1</span>
                  </li>
               </ul>
            </div>
            <div class="card-footer text-muted">
               2 days ago
            </div>
         </div>
      </div>
   </div>
</div>
`
});