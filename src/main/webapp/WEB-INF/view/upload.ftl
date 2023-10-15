<html lang="en">
<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles></#macro>

<#macro title>Upload file</#macro>

<#macro content1>
    <p>Upload file</p>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <br>
        <input type="submit" value="upload">
    </form>
</#macro>
<#macro content2>

</#macro>

</html>
