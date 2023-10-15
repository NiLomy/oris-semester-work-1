<html lang="en">
<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles></#macro>

<#macro title>Login</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Please log in</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <form action="login" method="post">
            <table align="center">
                <tr>
                    <td>
                        <b>Login:</b>
                    </td>
                    <td>
                        <input type="text" name="login"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Password:</b>
                    </td>
                    <td>
                        <input type="password" name="password"/>
                    </td>
                </tr>
            </table>
            <br>
            <input type="submit" value="login"/>
            <br>
        </form>
        <input type="checkbox" name="remember_me">
        <label for="remember_me">Remember me</label>

        <form action="registration" method="get">
            <button type="submit">
                Go to register page
            </button>
        </form>
    </div>
</#macro>
</html>
