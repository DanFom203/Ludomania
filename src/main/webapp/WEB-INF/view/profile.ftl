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

            <#include "balance_form.ftl">

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