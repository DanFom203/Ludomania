<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="resources/css/sign-in.css">
    <link rel="stylesheet" href="resources/css/error_message.css">
    <style>
        .to-sign-in-button {
            border: 2px solid CornflowerBlue;
            background-color: cornflowerblue;
            border-radius: 37px;
            width: 200px;
            height: 80px;
            margin: 30px auto auto;
            display: block;
            color: white;

            text-decoration: none;
            font-size: 27px;

            padding: 0;
            line-height: 80px;

            text-align: center;
        }
    </style>
</head>
<body>

<form class="form-center-content" method="post">
    <div class="form-signin-heading">Sign Up</div>
    <label>First name
        <input class="form-control" name="firstName" type="text">
    </label>
    <label>Last name
        <input class="form-control" name="lastName" type="text">
    </label>
    <label>Email
        <input class="form-control" name="email" type="email">
    </label>
    <label>Password
        <input class="form-control" name="password" type="password">
    </label>
    <label>Birthdate
        <input class="form-control" name="birthdate" type="date">
    </label>
    <#if errorMessage??>
        <div class="error_message" id="error_message">
            <span class="close_button">✖</span>
            ${errorMessage}
        </div>
    </#if>
    <input class="login-button" type="submit" value="Sign-up">
    <input class="to-sign-in-button" type="button" value="To sign-in page">
</form>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const toSignInButton = document.querySelector('.to-sign-in-button');

        // Обработчик события клика на кнопке
        toSignInButton.addEventListener('click', function () {
            // Переход на страницу /sign-in
            window.location.href = '/case-opening/sign-in';
        });
    });
</script>

</body>
</html>