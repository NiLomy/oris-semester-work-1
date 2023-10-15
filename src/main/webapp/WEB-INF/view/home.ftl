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
                    <li style="display: inline-block;"><a href="${pageContext}/posts" style="display: block;
    font-size: 1.1em;
    text-decoration: none;
    padding: 0.5em 1em;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Posts</a> </li>
                    <#if currentUser? has_content>
                        <li style="display: inline-block;"><a href="${pageContext}/profile" style="display: block;
    font-size: 1.1em;
    padding: 0.5em 1em;
    text-decoration: none;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Profile</a> </li>
                    <#else>
                        <li style="display: inline-block;"><a href="${pageContext}/login" style="display: block;
    font-size: 1.1em;
    padding: 0.5em 1em;
    text-decoration: none;
    -webkit-transition: 0.9s;
    -moz-transition: 0.9s;
    -o-transition: 0.9s;
    transition: 0.9s;">Login</a> </li>
                    </#if>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</header>

<#include "base.ftl">
<#macro scripts></#macro>
<#macro styles></#macro>
<#macro title>Home</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>AAAaaaa</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <form action="login" method="get">
            <button type="submit">
                Go to register page
            </button>
        </form>
        <br>
        <form action="profile" method="get">
            <button type="submit">
                Go to profile page
            </button>
        </form>
        <br>
        <form action="posts" method="get">
            <button type="submit">
                Go to posts
            </button>
        </form>
    </div>
</#macro>
</html>
