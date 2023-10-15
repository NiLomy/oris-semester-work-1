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
            "click", "#submit-button", function () {
                let nickname = $("#nickname").val();
                let name = $("#name").val();
                let lastname = $("#lastname").val();
                let email = $("#email").val();

                $.post(
                    "${pageContext}/edit_profile", {
                        "nickname": nickname,
                        "name": name,
                        "lastname": lastname,
                        "email": email,
                    }, function (response) {
                        $("#edit-status").text(response)
                    }
                )
            }
        )

        $(document).on(
            "click", "#change-password-button", function () {
                let currentPassword = $("#current-password").val()
                let newPassword = $("#new-password").val()
                let repeatPassword = $("#new-password-repeat").val()

                $.post(
                    "${pageContext}/password-change", {
                        "currentPassword": currentPassword,
                        "newPassword": newPassword,
                        "repeatPassword": repeatPassword
                    }, function (response) {
                        $("#edit-status").text(response)
                    }
                )
            }
        )

        $(document).ready(function () {
            $("#image-change").click(function (event) {

                //stop submit the form, we will post it manually.
                event.preventDefault();

                // Get form
                var form = $('#image-form')[0];

                // Create an FormData object
                var data = new FormData(form);

                // disabled the submit button
                $("#image-change").prop("disabled", true);

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "${pageContext}/upload",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {

                        $("#edit-status").text(data);
                        console.log("SUCCESS : ", data);
                        $("#image-change").prop("disabled", false);

                    },
                    error: function (e) {

                        $("#edit-status").text(e.responseText);
                        console.log("ERROR : ", e);
                        $("#image-change").prop("disabled", false);

                    }
                });

            });

        });
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Edit Profile</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Change your data</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <form id="image-form" action="upload" method="post" enctype="multipart/form-data">
            <img src="${currentUser.imageUrl}" alt="Profile Image" style="border-radius: 50%" width="150px" height="150px">
            <br>
            <input id="file" type="file" name="file">
            <br>
            <input id="image-change" type="button" value="upload">
        </form>
        <br>
        <form action="edit_profile" method="post" id="edit-profile-form">
            <table align="center">
                <tr>
                    <td>Login: </td>
                    <td><input id="nickname" type="text" name="nickname" maxlength="60" value="${currentUser.login}"></td>
                </tr>
                <tr>
                    <td>Name: </td>
                    <td><input id="name" type="text" name="name" maxlength="60" value="${currentUser.firstName}"></td>
                </tr>
                <tr>
                    <td>Lastname: </td>
                    <td><input id="lastname" type="text" name="lastname" maxlength="60" value="${currentUser.lastName}"></td>
                </tr>
                <tr>
                    <td>Email: </td>
                    <td><input id="email" type="text" name="email" maxlength="60" value="${currentUser.email}"></td>
                </tr>
            </table>
            <br>
            <input type="button" value="Save changes" id="submit-button">
        </form>
        <br>
        <form id="change-password-form">
            <table align="center">
                <tr>
                    <td>Current password: </td>
                    <td><input id="current-password" type="password" name="current-password" maxlength="60"></td>
                </tr>
                <tr>
                    <td>New password: </td>
                    <td><input id="new-password" type="password" name="new-password" maxlength="60"></td>
                </tr>
                <tr>
                    <td>Repeat password: </td>
                    <td><input id="new-password-repeat" type="password" name="repeat-password" maxlength="60"></td>
                </tr>
            </table>
            <br>
            <input type="button" value="Change password" id="change-password-button">
        </form>
    </div>
    <br>
    <div align="center" id="edit-status"></div>
</#macro>
</html>
