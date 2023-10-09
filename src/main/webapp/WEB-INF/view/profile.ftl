<html lang="en">

<header style="align-items: center;">
    <div class="wrap" style="margin: 0 auto; width: max-content; align-items: center;">
        <div class="header" style="padding: 0.3em 0;">
            <div class="nav">
                <ul>
                    <li class="active" style="display: inline-block;"><a href="${pageContext}/home" style="display: block;
    font-size: 1.1em;
    text-decoration: none;
    padding: 0.5em 1em;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Main</a> </li>
                    <li style="display: inline-block;"><a href="${pageContext}/login" style="display: block;
    font-size: 1.1em;
    text-decoration: none;
    padding: 0.5em 1em;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Login</a> </li>
                    <li style="display: inline-block;"><a href="${pageContext}/logout" style="display: block;
    font-size: 1.1em;
    padding: 0.5em 1em;
    text-decoration: none;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Logout</a> </li>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</header>

<#include "base.ftl">
<#macro title>Profile</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>This is your profile</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center" align="center">
        ${currentUser.firstName} ${currentUser.lastName}
        <table style="align-content: center; align-items: center; text-align: center">
            <tr>
                <td>Login: </td>
                <td>${currentUser.login}</td>
            </tr>
            <tr>
                <td>Email: </td>
                <td>${currentUser.email}</td>
            </tr>
        </table>
        <br>
        <a href="${pageContext}/edit_profile">Edit profile</a>
    </div>
</#macro>
</html>
