const errorMessage = "${errorMessage?if_exists}";
if (errorMessage && errorMessage.trim() !== "") {
    // Если есть, показываем всплывающее окно
    const errorMessageBox = document.getElementById('error_message');
    errorMessageBox.style.display = 'block';

    // Функция для закрытия всплывающего окна
    function closeErrorMessage() {
        errorMessageBox.style.display = 'none';
    }

    // Назначаем функцию на кнопку закрытия
    const closeButton = document.querySelector('.close_button');
    closeButton.addEventListener('click', closeErrorMessage);
}