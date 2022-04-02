'use strict';

app.component('logs', {
    controller: function (RestAPI, $scope) {

    }, template: `
<div class="container">
   <div class="row justify-content-md-center" style="display: block">
      <div class="row" style="padding: 40px;">
         <div class="col-lg-12"></div>
         <div class="col-lg-12">
            <table class="table table-dark table-hover">
               <thead>
                  <tr>
                     <th scope="col">#</th>
                     <th scope="col">First</th>
                     <th scope="col">Last</th>
                     <th scope="col">Handle</th>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <th scope="row">1</th>
                     <td>Mark</td>
                     <td>Otto</td>
                     <td>@mdo</td>
                  </tr>
                  <tr>
                     <th scope="row">2</th>
                     <td>Jacob</td>
                     <td>Thornton</td>
                     <td>@fat</td>
                  </tr>
                  <tr>
                     <th scope="row">3</th>
                     <td colspan="2">Larry the Bird</td>
                     <td>@twitter</td>
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
