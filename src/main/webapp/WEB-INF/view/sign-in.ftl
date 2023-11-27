<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="resources/css/sign-in.css">
    <link rel="stylesheet" href="resources/css/error_message.css">
</head>
<body>
<div class="container">

    <form class="form-center-content" method="post">
        <div class="form-signin-heading">Sign in</div>
        <label>
            <input class="form-control" name="email" type="email" placeholder="Email">
        </label>
        <label>
            <input class="form-control" name="password" type="password" placeholder="Password">
        </label>
        <#if errorMessage??>
            <div class="error_message" id="error_message">
                <span class="close_button">✖</span>
                ${errorMessage}
            </div>
        </#if>
        <input class="login-button" type="submit">
    </form>

</div>
<script src="resources/js/error_message_scripts.js"></script>
</body>
</html>