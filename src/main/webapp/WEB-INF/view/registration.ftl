<html lang="en">
<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles></#macro>

<#macro title>Registration</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Register in</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <form action="registration" method="post">
            <table align="center">
                <tr>
                    <td>
                        <b>Name:</b>
                    </td>
                    <td>
                        <input type="text" name="name"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Lastname:</b>
                    </td>
                    <td>
                        <input type="text" name="lastname"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Email:</b>
                    </td>
                    <td>
                        <input type="text" name="email"/>
                    </td>
                </tr>
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
            <input type="submit" value="registration"/>
        </form>
        <br>
        <input type="checkbox" name="remember_me">
        <label for="remember_me">Remember me</label>

        <br>
        <form action="login" method="get">
            <button type="submit">
                Already registered
            </button>
        </form>
    </div>
    <div style="text-align: center">
        <#if isEmailAlreadyExists?has_content>
            <b>This email already in use</b>
        </#if>
        <#if isAlreadyRegistered?has_content>
            <b>You are already registered</b>
        </#if>
    </div>
</#macro>
</html>
