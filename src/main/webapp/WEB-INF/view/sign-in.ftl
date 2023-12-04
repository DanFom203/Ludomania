<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="resources/css/style3.css">
    <link rel="stylesheet" href="resources/css/sign-in.css">
    <link rel="stylesheet" href="resources/css/menu.css">
    <link rel="stylesheet" href="resources/css/error_message.css">
</head>
<body>
<div class="menu">
    <#include "menu.ftl">
</div>
<div class="wrapper">
    <div class="login-text">
        <button class="cta"><i class="fas fa-chevron-down fa-1x"></i></button>
        <div class="text">
            <form class="form-center-content" method="post">
                <a href="">Login</a>
                <hr>
                <br>
                <label>
                    <input class="form-control" name="email" type="email" placeholder="Email">
                </label>
                <br>
                <label>
                    <input class="form-control" name="password" type="password" placeholder="Password">
                </label>
                <br>
                <#if errorMessage??>
                    <div class="error_message" id="error_message">
                        <span class="close_button">✖</span>
                        ${errorMessage}
                    </div>
                </#if>
                <button class="login-btn">Sign-in</button>
            </form>
        </div>
    </div>
    <div class="call-text">
        <h1>Show your <span>luck</span></h1>
        <button class="to-signup-button">To sign-up page</button>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const toSignUpButton = document.querySelector('.to-signup-button');

        // Обработчик события клика на кнопке
        toSignUpButton.addEventListener('click', function () {
            // Переход на страницу /sign-in
            window.location.href = '/case-opening/sign-up';
        });
    });
</script>
<script src="resources/js/sign-in.js"></script>
<script src="resources/js/error_message_scripts.js"></script>
</body>
</html>