$(document).ready(function(){
    $("#openCase").click(function(){

        if ($(this).hasClass('disabled')) {
            return false;
        } else {
            startRoll();
        }

    });
});

function saveAndUpdate(skinName, balance, price) {
    // Выполняем сначала submitSkinSave
    submitSkinSave(skinName);

    // Затем вызываем updateBalance
    updateBalance(balance, price);
}

function updateBalance(balance, price) {
    var openCaseButton = document.getElementById("openCase");

        submitOpenCase(price);
    // }
}

function submitOpenCase(price) {
    var xhr = new XMLHttpRequest();

    var servletUrl = "balance/update";

    xhr.open("POST", servletUrl, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var data = "casePrice=" + price ;

    xhr.send(data);
}

function startRoll(){

    $("#openCase").html("Rolling...").addClass("disabled");

    var lineArrays = ['2985px', '2995px', '3005px', '3015px', '3025px', '3035px', '3045px', '3055px', '3065px', '3075px', '3085px', '3095px', '3100px'];

    var landLine = lineArrays[Math.floor(Math.random() * lineArrays.length)];
    console.log(landLine);

    var itemBoxes = document.getElementsByClassName("itemBoxAn");
    $(itemBoxes).animate(
        {right: landLine},
        {
            duration: 12000,
            easing: 'easeOutQuint',
            complete: function() {
                openPopup(itemBoxes[23]);
            }
        }
    );
}
function openPopup(element) {
    var popup = document.getElementById('popup');
    var overlay = document.getElementById('overlay');

    // Заполните эти переменные данными о скине
    var skinName = element.getAttribute('data-skin-name');
    var skinRarity = element.getAttribute('data-skin-rarity');
    var skinImageSrc = element.getAttribute('data-skin-image');
    var skinPrice = element.getAttribute('data-skin-price');

    // Заполнение контента всплывающего окна
    document.getElementById('popupName').innerText = skinName;
    document.getElementById('popupRarity').innerText = skinRarity;
    document.getElementById('popupImage').src = skinImageSrc;
    document.getElementById("popupPrice").innerText = skinPrice;

    // Показать всплывающее окно и оверлей
    popup.style.display = 'block';
    overlay.style.display = 'block';
}

function closePopup() {
    var popup = document.getElementById('popup');
    var overlay = document.getElementById('overlay');

    // Скрыть всплывающее окно и оверлей
    popup.style.display = 'none';
    overlay.style.display = 'none';

    // location.reload();
}

function submitSkinSave(skinName) {

    var xhr = new XMLHttpRequest();

    var servletUrl = "skin/save";

    xhr.open("POST", servletUrl, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var data = "skinName=" + skinName ;

    xhr.send(data);

}