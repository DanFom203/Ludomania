<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>

    <link href="resources/css/style1.css" rel="stylesheet">
    <link href="resources/css/menu.css" rel="stylesheet">
    <link href="resources/css/profile.css" rel="stylesheet">

    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/profile.js"></script>
    <script>
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
    </script>
</head>
<body>
<div class="menu">
    <#include "authorizedmenu.ftl">
</div>
<div class="container">
    <div class="center-content">
        <div class="container">
            <div class="title">Профиль</div>
            <div id="profile" class="white-container">

                <#if user.avatarId??>
                <img class="user-avatar" alt="IMAGE" src="files/${user.avatarId}" style="width:304px;height:228px;"/>
                <#else>
                <img class="user-avatar" alt="IMAGE" src="resources/img/no-avatar.png" style="width:304px;height:228px;"/>
                </#if>

                <div class="button"><a href="fileUpload.ftl">Изменить аватарку</a></div>


                <div class="user-info-text">
                    <div class="user-info">FIRST NAME: ${user.firstName}</div>
                    <div class="user-info">LAST NAME: ${user.lastName}</div>
                    <div class="user-info">EMAIL: ${user.email}</div>
                    <div class="user-info">DATE OF BIRTH: ${user.birthdate}</div>
                    <div class="user-balance-info">YOUR BALANCE: ${user.balance}</div>
                </div>

            </div>

            <form id="add-post-form" action="balance/donate" method="post">
                <div>Top up your balance</div>
                <input type="number" name="balance" placeholder="Внесите сумму" required>
                <input id="submitBalance" type="submit" name="Пополнить счёт">
            </form>

            <div class="divider"></div>

        </div>


    </div>
</div>
<div id="user-skins" class="white-container">
    <div class="title">Ваши скины</div>

    <#list usersSkins as skin>
        <div class="user-skin">
            <#if skin.rarity == "Тайное">
                <#assign rarityClass = 'red'>
            <#elseif skin.rarity == "Засекреченное">
                <#assign rarityClass = 'mediumvioletred'>
            <#elseif skin.rarity == "Запрещённое">
                <#assign rarityClass = 'rebeccapurple'>
            <#elseif skin.rarity == "Армейское качество">
                <#assign rarityClass = 'blue'>
            </#if>
            <img src="${skinImagePath}${skin.getId()}.png" alt="${skin.getName()}" style="width:150px;height:165px; background: ${rarityClass}">
            <div class="skin-info">
                <div class="skin-name-rarity">${skin.getName()} - ${skin.getRarity()}</div>
            </div>
        </div>
    </#list>
</div>

</body>
</html>