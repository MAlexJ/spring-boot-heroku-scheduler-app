'use strict';

app.component('settings', {
    controller: function ($location, $window, $state, $timeout) {
        let url = $location.absUrl().concat('-ui.html');
        // $window.open(url, '_blank');
        // $timeout(function () {
        //     window.location.replace($location.absUrl());
        // }, 200);
    }, template: `

  <div class="container">
    <h1 class="mt-5">Sticky footer</h1>
    <p class="lead">Pin a footer to the bottom of the viewport in desktop browsers with this custom HTML and CSS.</p>
    <p>Use <a href="/docs/5.0/examples/sticky-footer-navbar/">the sticky footer with a fixed navbar</a> if need be, too.</p>
  </div>
`
});