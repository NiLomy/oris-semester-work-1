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
        $(document).on(
            "click", "#like-button", function () {

                $.post(
                    "${pageContext}/post", {
                        "action": 'pressLike'
                    }, function (response) {
                        $("#likes").text(response)
                    }
                )
            }
        )

        $(document).on(
            "click", "#send-message-button", function () {
                let newMessage = $("#new-message").val();

                $.post(
                    "${pageContext}/post", {
                        "action": "sendMessage",
                        "newMessage": newMessage
                    }, function (response) {
                        $("#messages").append(
                            '<div>' + response.author + ' ' + response.date + '<br>' + response.messageContent + '<br>' + response.likes + '<br><br></div>'
                        )
                    }
                )
            }
        )
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Post</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>${currentPost.name}</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        ${currentPost.category} ${currentPost.author} ${currentPost.date}
        <br>
        <br>
        ${currentPost.content}
        <br>
        <div id="likes">${currentPost.likes}</div>
        <input type="button" value="Like" id="like-button">
        <br>
        <div id="messages">
            <div>
                <#if messages?has_content>
                    <#list messages as message>
                        ${message.author} ${message.date}
                        <br>
                        ${message.content}
                        ${message.likes}
                        <br>
                        <br>
                    </#list>
                </#if>
            </div>
        </div>
        <br>
        <div>
            <input id="new-message" type="text" name="name">
            <input type="button" value="Send message" id="send-message-button">
        </div>
        <div id="test"></div>
    </div>
    <br>
</#macro>
</html>
