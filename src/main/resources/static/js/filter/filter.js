'use strict';

app.filter('convertTohHshCode', function() {
    return function(str) {
        return str.split('') //
            .reduce((prevHash, currVal) => (((prevHash << 5) - prevHash) + currVal.charCodeAt(0)) | 0, 0);
    };
});