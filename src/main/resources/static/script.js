$(document).ready(function() {
    $('#uploadButton').click(function() {
        var fileInput = $('#fileInput')[0].files[0];

        var formData = new FormData();
        formData.append('file', fileInput);

        $.ajax({
            url: '/api/image/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                console.log(data);
                $('#message').text('Image uploaded successfully.');
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                $('#message').text('Error occurred while uploading image.');
            }
        });
    });
});
