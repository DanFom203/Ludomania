<html lang="eng">
<head>
    <title>Main</title>
    <link href="resources/css/menu.css" rel="stylesheet">
</head>
<body>
<div class="menu">
    <#include "authorizedmenu.ftl">
</div>

<div class="content">
    <div class="case-list-section">
        <h2>Список кейсов</h2>
        <#list casesList as case>
            <div class="case-item">
                <img src="${imagePath}${case.getId()}.jpeg" alt="${case.getName()}" style="width:304px;height:228px;">
                <form action="openCasePage" method="post">
                    <input type="hidden" name="caseId" value="${case.getId()}">
                    <input type="submit" value="Открыть кейс">
                </form>
            </div>
        </#list>
    </div>
</div>
</body>
</html>

