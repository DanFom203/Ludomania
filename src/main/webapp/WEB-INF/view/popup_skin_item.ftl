<head>

    <style>
        #popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
            z-index: 1000;
        }

        #overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }
    </style>

    <script src="resources/case-opening-animation/custom.js"></script>
</head>
<body>
<!-- Контент страницы -->
<div id="overlay"></div>
<!-- Всплывающее окно -->
<form class="form-skin-save-content" action="skin/save" >
    <div id="popup">
        <h2 id="popupName"></h2>
        <p id="popupRarity"></p>
        <p id="popupPrice"></p>
        <img id="popupImage" alt="Skin Image">
        <button onclick="closePopup()">Открыть еще один кейс</button>
    </div>
</form>

</body>