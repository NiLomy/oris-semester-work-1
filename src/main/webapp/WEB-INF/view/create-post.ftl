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
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        <#--$(document).on(-->
        <#--    "click", "#submit-button", function () {-->
        <#--        let postName = $("#post-name").val();-->
        <#--        let postCategory = $("#post-category").val();-->
        <#--        let postText = $("#post-text").val();-->

        <#--        $.post(-->
        <#--            "${pageContext}/create-post", {-->
        <#--                "postName": postName,-->
        <#--                "postCategory": postCategory,-->
        <#--                "postText": postText-->
        <#--            }, function (response) {-->
        <#--                $("#edit-status").text(response)-->
        <#--            }-->
        <#--        )-->
        <#--    }-->
        <#--)-->
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Create Post</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Create Post</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <h2>Add your post</h2>
        <br>
        <form method="post" action="create-post" id="create-post-form">
            <table align="center">
                <tr>
                    <td>Post name: </td>
                    <td><input id="post-name" type="text" name="postName" maxlength="60"></td>
                </tr>
                <tr>
                    <td>Choose your category: </td>
                    <td><select id="post-category" name="postCategory">
                            <option selected disabled value="">Choose one of this categories</option>
                            <option>Christianity</option>
                            <option>Islam</option>
                            <option>Judaism</option>
                            <option>Other</option>
                        </select></td>
                </tr>
            </table>
            <br>
            <label>
                Enter your post text:
                <input id="post-text" name="postText" type="text">
            </label>
            <br>
            <button id="submit-button" type="submit">
                Create post
            </button>
<#--            <input type="button" value="Create post" id="submit-button">-->
        </form>
        <br>
        <div id="edit-status"></div>
    </div>
</#macro>
</html>
