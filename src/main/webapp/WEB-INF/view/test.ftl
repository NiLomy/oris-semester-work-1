<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
</head>
<body>
<form action="<@spring.url '/test'/>" method="post">
    Login:
    <input type="text" name="username" id="username"/>
    <br>
    Password:
    <input type="password" name="password" id="password"/>
    <br>
    <input type="submit" value="press me please">
    <input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
</form>
</body>
</html>