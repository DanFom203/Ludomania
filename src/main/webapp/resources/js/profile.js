$(document).ready(function(){
    $("#submitBalance").click(function(e){
        e.preventDefault(); // Предотвращаем отправку формы по умолчанию

        var formData = $("#add-post-form").serialize(); // Получаем данные формы

        $.ajax({
            type: "POST",
            url: "balance/donate",
            data: formData,
            success: function(response) {
                // Обновление информации на странице /profile
                // Замена содержимого блока #profile с новыми данными
                $("#profile").html($(response).find("#profile").html());

                // Обновление баланса в блоке user-balance-info
                $(".user-balance-info").html("YOUR BALANCE: " + response.balance);
            },
            error: function(error) {
                console.log("Error:", error);
            }
        });
    });
});