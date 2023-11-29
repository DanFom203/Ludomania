<html lang="eng">
<head>
    <title>Main</title>
    <link href="resources/css/menu.css" rel="stylesheet">
    <link href="resources/css/main.css" rel="stylesheet">
    <link href= "resources/css/mainpage.css" rel="stylesheet">
</head>
<body>
<div class="menu">
    <#include "authorizedmenu.ftl">
</div>
<div class="content">
    <div class="create-case-section">
        <form class="create-case-section" method="post">
            <label for="caseName">Case name</label>
            <input type="text" id="caseName" name="caseName" required/>
            <input type="submit" value="Create Case">
        </form>
    </div>

    <#include "case/caselist.ftl">

</div>
</body>
</html>