$(document).ready(function () {
    $("#MyModal").modal();
    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').focus();
    });
});