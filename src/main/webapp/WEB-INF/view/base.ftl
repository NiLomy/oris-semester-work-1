<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title></@title></title>

    <@scripts></@scripts>
    <@styles></@styles>
    <script src="https://kit.fontawesome.com/b8991598b2.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<#include "navbar.ftl">

<div class="content">
    <div class="content1">
        <div style="font-size:150%; text-align:center" class="mt-5">
            <@content1></@content1>
        </div>
    </div>
    <br>
    <div class="content2"><@content2></@content2></div>
</div>
</body>
<#include "footer.ftl">
</html>
