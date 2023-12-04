<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="resources/css/style3.css">
    <link rel="stylesheet" href="resources/css/sign-in.css">
    <link rel="stylesheet" href="resources/css/menu.css">
    <link rel="stylesheet" href="resources/css/error_message.css">
    <link rel="stylesheet" href="resources/css/sign-up-style.css">
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
                <a href="">Registration</a>
                <hr>
                <br>
                <input name="firstName" type="text" placeholder="First name">
                <br>
                <input name="lastName" type="text" placeholder="Last name">
                <br>
                <input name="email" type="email" placeholder="Email">
                <br>
                <input name="password" type="password" placeholder="Password">
                <br>
                <input name="birthdate" type="date">
                <br>
                <#if errorMessage??>
                    <div class="error_message" id="error_message">
                        <span class="close_button">✖</span>
                        ${errorMessage}
                    </div>
                </#if>
                <button class="signup-btn">Sign-up</button>
            </form>
        </div>
    </div>
    <div class="call-text">
        <h1>Show your <span>luck</span></h1>
        <button class="to-signin-button">To sign-in page</button>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const toSignInButton = document.querySelector('.to-signin-button');

        toSignInButton.addEventListener('click', function () {
            window.location.href = '/case-opening/sign-in';
        });
    });
</script>
<script src="resources/js/error_message_scripts.js"></script>
<script src="resources/js/sign-in.js"></script>
</body>
</html>