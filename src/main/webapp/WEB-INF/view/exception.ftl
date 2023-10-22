<html lang="en">
<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles></#macro>

<#macro title>
    Exception details
</#macro>

<#macro content1></#macro>

<#macro content2>
    <h1>exception details:</h1>
    <strong>Request uri:</strong>${uri}<br>
    <strong>Status code:</strong>${statusCode}<br>
    <#if message??><strong>Status code:</strong>${message}<br></#if>
</#macro>

</html>
