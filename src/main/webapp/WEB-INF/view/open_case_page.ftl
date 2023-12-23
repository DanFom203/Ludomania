<html lang="en">
<head>
    <title>OpenCase</title>
    <link href="resources/css/menu.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/case-opening-animation/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <script src="resources/js/case-opening.js"></script>

    <#include "popup_skin_item.ftl">

</head>
<body>

<button type="button" id="goBackButton" class="btn btn-secondary">Go back</button>

<div class="content">
    <div class="case-list-section">

        <h2>Открыть кейс</h2>
        <div class="case-item">
            <img src="${caseImagePath}" alt="${selectedCase.getName()}" style="width:304px;height:228px;">

            <div class="container-fluid caseOpeningArea">
                <div class="row">

                    <div class="container animationAreaItems">
                        <div class="row">
                            <div class="row text-center flex-nowrap mx-auto">

                                <#list randomSkins as skin>
                                    <#if skin.rarity == "Тайное">
                                        <#assign rarityClass = 'red'>
                                    <#elseif skin.rarity == "Засекреченное">
                                        <#assign rarityClass = 'mediumvioletred'>
                                    <#elseif skin.rarity == "Запрещённое">
                                        <#assign rarityClass = 'rebeccapurple'>
                                    <#elseif skin.rarity == "Армейское качество">
                                        <#assign rarityClass = 'blue'>
                                    </#if>
                                    <div class="itemBoxAn"
                                         data-skin-name="${skin.name}"
                                         data-skin-rarity="${skin.rarity}"
                                         data-skin-image="${skinImagePath}${skin.getId()}.png"
                                         data-skin-price="Цена : ${skin.price} руб.">
                                        <img src="${skinImagePath}${skin.getId()}.png" alt="${skin.getName()}" style="width:150px;height:165px;background: ${rarityClass}">
                                    </div>
                                </#list>

                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <button type="button" id="openCase"
                    onclick="saveAndUpdate('${randomSkins[23].name}', '${user.balance}', '${selectedCase.price}')"
                    class="btn btn-info copenbtn"
                    <#if selectedCase.price gt user.balance>disabled</#if>
            > OPEN CASE FOR ${selectedCase.price}
            </button>

            <#list skinsList as skin>
                <ul class="skin">
                    <li>${skin.getName()} - ${skin.getRarity()}</li>
                </ul>
            </#list>

        </div>

    </div>
</div>

<script>
    document.getElementById('goBackButton').addEventListener('click', function() {

        // window.history.back();
        window.location.href = '/case-opening/main';
    });
</script>

</body>
</html>