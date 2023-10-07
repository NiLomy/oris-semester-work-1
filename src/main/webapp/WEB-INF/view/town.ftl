<html lang="en">
<#include "base.ftl">

<#macro title>Town</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        Enter the town whose whether you want to know
    </div>
</#macro>

<#macro content2>
    <form action="town" method="post">
        <div style="font-size:110%; text-align: center">
            <b>
                Town:
            </b>
            <input type="text" name="town"/>
            <br>
            <br>
            <input type="submit" value="continue"/>
        </div>
    </form>
</#macro>
</html>
