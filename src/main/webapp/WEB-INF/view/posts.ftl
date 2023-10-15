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

<#macro scripts>
</#macro>
<#macro styles></#macro>

<#macro title>Posts</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Posts</b>
    </div>
</#macro>
<#macro content2>
    <a class="btn btn-primary" href="${pageContext}/create-post">Add post</a>
    <br>
    <br>
    <div style="font-size:110%; text-align: center">
        <#if posts? has_content>
            <#list posts as p>
                ${p.name}
                <br>
                ${p.category}
                <br>
                ${p.likes}
                <br>
                <a href="${pageContext}/post?postName=${p.name}&postAuthor=${p.author}">Go to page</a>
                <br>
                <br>
            </#list>
        </#if>
    </div>
</#macro>
</html>
