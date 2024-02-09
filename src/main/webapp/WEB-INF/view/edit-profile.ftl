<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#image-change").click(function (event) {
                event.preventDefault();

                const form = $('#image-form')[0];

                const data = new FormData(form);

                $("#image-change").prop("disabled", true);

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "${pageContext}/edit-profile",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        $("#profile-img").attr("src", data)
                        $("#navProfileImage").attr("src", data)
                        alert("Your image was successfully changed!");
                        $("#image-change").prop("disabled", false);
                    },
                    error: function (e) {
                        alert(e.responseText);
                        $("#image-change").prop("disabled", false);
                    }
                });
            });
        });

        $(document).ready(function () {

            // Name validation
            let usernameError = true;
            $("#name").keyup(function () {
                validateName();
            });

            function validateName() {
                let nameValue = $("#name").val().trim();
                if (nameValue.length === 0) {
                    $("#name-error").text("You should enter your name");
                    $("#name").addClass("is-invalid");
                    usernameError = false;
                    return false;
                } else if (nameValue.length > 60) {
                    $("#name-error").text("Too long name");
                    $("#name").addClass("is-invalid");
                    usernameError = false;
                    return false;
                } else {
                    $("#name-error").text("");
                    $("#name").removeClass("is-invalid");
                    usernameError = true;
                }
            }

            // Lastname validation
            let lastnameError = true;
            $("#lastname").keyup(function () {
                validateLastname();
            });

            function validateLastname() {
                let lastnameValue = $("#lastname").val().trim();
                if (lastnameValue.length === 0) {
                    $("#lastname-error").text("You should enter your lastname");
                    $("#lastname").addClass("is-invalid");
                    lastnameError = false;
                    return false;
                } else if (lastnameValue.length > 60) {
                    $("#lastname-error").text("Too long lastname");
                    $("#lastname").addClass("is-invalid");
                    lastnameError = false;
                    return false;
                } else {
                    $("#lastname-error").text("");
                    $("#lastname").removeClass("is-invalid");
                    lastnameError = true;
                }
            }

            // Nickname validation
            let nicknameError = true;
            $("#nickname").keyup(function () {
                validateNickname();
            });

            function validateNickname() {
                let nicknameValue = $("#nickname").val().trim();
                if (nicknameValue.length === 0) {
                    $("#nickname-error").text("You should enter your nickname");
                    $("#nickname").addClass("is-invalid");
                    nicknameError = false;
                    return false;
                } else if (nicknameValue.length > 60) {
                    $("#nickname-error").text("Too long nickname");
                    $("#nickname").addClass("is-invalid");
                    nicknameError = false;
                    return false;
                } else if (nicknameValue.length < 5) {
                    $("#nickname-error").text("Too short nickname");
                    $("#nickname").addClass("is-invalid");
                    nicknameError = false;
                    return false;
                } else {
                    $("#nickname-error").text("");
                    $("#nickname").removeClass("is-invalid");
                    nicknameError = true;
                }
            }

            // Email validation
            let emailError = true
            $("#email").keyup(function () {
                validateEmail();
            })

            function validateEmail() {
                let s = $("#email").val().trim();
                if (s.length === 0) {
                    $("#email-error").text("You should enter your email")
                    $("#email").addClass("is-invalid");
                    emailError = false;
                    return false;
                }
                var regex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
                if (regex.test(s)) {
                    $("#email-error").text("")
                    $("#email").removeClass("is-invalid");
                    emailError = true;
                } else {
                    $("#email-error").text("Invalid email")
                    $("#email").addClass("is-invalid");
                    emailError = false;
                }
            }

            // Current password validation
            let currentPasswordError = true;
            $("#current-password").keyup(function () {
                validateCurrentPassword();
            });

            function validateCurrentPassword() {
                let passwordValue = $("#current-password").val();
                if (passwordValue.length === 0) {
                    $("#current-password-error").text("You should enter your current password");
                    $("#current-password").addClass("is-invalid");
                    currentPasswordError = false;
                    return false;
                } else {
                    $("#current-password-error").text("");
                    $("#current-password").removeClass("is-invalid");
                    currentPasswordError = true;
                    return true;
                }
            }

            // New password validation
            let passwordError = true;
            $("#new-password").keyup(function () {
                validateNewPassword();
            });

            function validateNewPassword() {
                let passwordValue = $("#new-password").val();
                if (passwordValue.length === 0) {
                    $("#new-password-error").text("You should enter your new password");
                    $("#new-password").addClass("is-invalid");
                    passwordError = false;
                    return false;
                }
                if (passwordValue.length < 8) {
                    $("#new-password-error").text("Password should be at least 8 characters long");
                    $("#new-password").addClass("is-invalid");
                    passwordError = false;
                    return false;
                } else {
                    $("#new-password-error").text("");
                    $("#new-password").removeClass("is-invalid");
                    passwordError = true;
                }
                var regex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
                if (regex.test(passwordValue)) {
                    $("#new-password-error").text("")
                    $("#new-password").removeClass("is-invalid");
                    passwordError = true;
                } else {
                    $("#new-password-error").text("Too weak password. Please use letters and digits");
                    $("#new-password").addClass("is-invalid");
                    passwordError = false;
                }
            }

            // Confirm password validation
            let confirmPasswordError = true;
            $("#new-password-repeat").keyup(function () {
                validateConfirmPassword();
            });

            function validateConfirmPassword() {
                let confirmPasswordValue = $("#new-password-repeat").val();
                let passwordValue = $("#new-password").val();
                if (passwordValue !== confirmPasswordValue) {
                    $("#repeat-password-error").text("Password doesn't match");
                    $("#new-password-repeat").addClass("is-invalid");
                    confirmPasswordError = false;
                    return false;
                } else {
                    $("#repeat-password-error").text("");
                    $("#new-password-repeat").removeClass("is-invalid");
                    confirmPasswordError = true;
                }
            }

            // Submit button
            $("#submit-button").click(function () {
                validateName();
                validateLastname();
                validateNickname();
                validateEmail();

                if (
                    usernameError === true &&
                    lastnameError === true &&
                    emailError === true &&
                    nicknameError === true
                ) {
                    let nickname = $("#nickname").val();
                    let name = $("#name").val();
                    let lastname = $("#lastname").val();
                    let email = $("#email").val();
                    let aboutMe = $("#about-me").val();
                    let emptyAboutMe = $("#empty-about-me").val();

                    $.post(
                        "${pageContext}/edit-profile", {
                            "action": "updateInfo",
                            "nickname": nickname,
                            "name": name,
                            "lastname": lastname,
                            "email": email,
                            "aboutMe": aboutMe,
                            "emptyAboutMe": emptyAboutMe
                        }, function (response) {
                            if (response === "emptyName") {
                                $("#name-error").text("You should enter your name");
                                $("#name").addClass("is-invalid");
                            } else if (response === "longName") {
                                $("#name-error").text("Too long name");
                                $("#name").addClass("is-invalid");
                            } else if (response === "emptyLastname") {
                                $("#lastname-error").text("You should enter your lastname");
                                $("#lastname").addClass("is-invalid");
                            } else if (response === "longLastname") {
                                $("#lastname-error").text("Too long lastname");
                                $("#lastname").addClass("is-invalid");
                            } else if (response === "emptyEmail") {
                                $("#email-error").text("You should enter your email")
                                $("#email").addClass("is-invalid");
                            } else if (response === "invalidEmail") {
                                $("#email-error").text("Invalid email")
                                $("#email").addClass("is-invalid");
                            } else if (response === "emptyNickname") {
                                $("#nickname-error").text("You should enter your nickname");
                                $("#nickname").addClass("is-invalid");
                            } else if (response === "shortNickname") {
                                $("#nickname-error").text("Too short nickname");
                                $("#nickname").addClass("is-invalid");
                            } else if (response === "longNickname") {
                                $("#nickname-error").text("Too long nickname");
                                $("#nickname").addClass("is-invalid");
                            } else if (response === "nonUniqueEmail") {
                                $("#email-error").text("User with this email is already registered")
                                $("#email").addClass("is-invalid");
                            } else if (response === "nonUniqueNickname") {
                                $("#nickname-error").text("User with this nickname is already registered");
                                $("#nickname").addClass("is-invalid");
                            } else {
                                alert("Your data was successfully changed!");
                            }
                        }
                    )
                }
            });

            $("#change-password-button").click(function () {
                validateCurrentPassword();
                validateNewPassword()
                validateConfirmPassword();

                if (
                    currentPasswordError === true &&
                    passwordError === true &&
                    confirmPasswordError === true
                ) {
                    let currentPassword = $("#current-password").val()
                    let newPassword = $("#new-password").val()
                    let repeatPassword = $("#new-password-repeat").val()

                    $.post(
                        "${pageContext}/edit-profile", {
                            "action": "changePassword",
                            "currentPassword": currentPassword,
                            "newPassword": newPassword,
                            "repeatPassword": repeatPassword
                        }, function (response) {
                            if (response === "emptyCurrentPassword") {
                                $("#current-password-error").text("You should enter your password");
                                $("#current-password").addClass("is-invalid");
                            } else if (response === "invalidPassword") {
                                $("#current-password-error").text("You enter wrong password");
                                $("#current-password").addClass("is-invalid");
                            } else if (response === "emptyNewPassword") {
                                $("#new-password-error").text("You should enter your new password");
                                $("#new-password").addClass("is-invalid");
                            } else if (response === "shortPassword") {
                                $("#new-password-error").text("Password should be at least 8 characters long");
                                $("#new-password").addClass("is-invalid");
                            } else if (response === "weakPassword") {
                                $("#new-password-error").text("Too weak password. Please use letters and digits");
                                $("#new-password").addClass("is-invalid");
                            } else if (response === "invalidConfirmPassword") {
                                $("#repeat-password-error").text("Password doesn't match");
                                $("#new-password-repeat").addClass("is-invalid");
                            } else {
                                alert("Your password was successfully changed!");

                            }
                        }
                    )
                }
            });
        })
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
    <section>
        <div class="profile-content container py-3">
            <div class="row d-flex justify-content-center align-items-center">
                <div class="ps-5 ms-5">
                    <div style="border-radius: 15px;">
                        <div class="p-4">
                            <div class="d-flex text-black">
                                <div class="flex-shrink-0 col-4 justify-content-end" align="center">
                                    <img src="${currentUser.imageUrl}"
                                         id="profile-img"
                                         alt="Profile image" class="img-fluid"
                                         style="width: 200px; border-radius: 10px;">
                                    <div class="ps-3 mt-1 d-flex pt-1">
                                        <form id="image-form" action="upload" method="post"
                                              enctype="multipart/form-data">
                                            <input id="file" class="form-control" type="file" name="file">
                                            <br>
                                            <div class="col-md-5">
                                                <input id="image-change"
                                                       class="btn btn-primary flex-grow-1 form-control" type="button"
                                                       value="upload">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="flex-grow-1 col-4 col-md-10 col-lg-8 col-xl-4">
                                    <section class="gradient-custom">
                                        <div class="container my-1 py-1">
                                            <div class="row d-flex justify-content-start">
                                                <div class="col-md-8 col-lg-6 col-xl-6">
                                                    <form action="edit-profile" method="post" id="edit-profile-form">
                                                        <div class="ps-2" style="text-align: left">
                                                            <label for="name">Name: </label><input id="name"
                                                                                                   class="form-control"
                                                                                                   type="text"
                                                                                                   name="name"
                                                                                                   maxlength="60"
                                                                                                   value="${currentUser.firstName}">
                                                            <p id="name-error" class="invalid-feedback d-block"
                                                               role="alert"></p>
                                                            <label for="lastname">Lastname: </label><input id="lastname"
                                                                                                           class="form-control"
                                                                                                           type="text"
                                                                                                           name="lastname"
                                                                                                           maxlength="60"
                                                                                                           value="${currentUser.lastName}">
                                                            <p id="lastname-error" class="invalid-feedback d-block"
                                                               role="alert"></p>
                                                            <label for="nickname">Nickname:</label><input id="nickname"
                                                                                                          class="form-control"
                                                                                                          type="text"
                                                                                                          name="nickname"
                                                                                                          maxlength="60"
                                                                                                          value="${currentUser.login}">
                                                            <p id="nickname-error" class="invalid-feedback d-block"
                                                               role="alert"></p>
                                                            <label for="email">Email: </label><input id="email"
                                                                                                     class="form-control"
                                                                                                     type="email"
                                                                                                     name="email"
                                                                                                     maxlength="60"
                                                                                                     value="${currentUser.email}">
                                                            <p id="email-error" class="invalid-feedback d-block"
                                                               role="alert"></p>
                                                            <#if currentUser.aboutMe? has_content>
                                                                <label for="about-me">About me: </label><input
                                                                    id="about-me" class="form-control" type="text"
                                                                    name="about-me" value="${currentUser.aboutMe}">
                                                            <#else>
                                                                <label for="empty-about-me">About me: </label><input
                                                                    id="empty-about-me" class="form-control" type="text"
                                                                    name="about-me">
                                                            </#if>
                                                            <br>
                                                            <input type="button" class="btn btn-primary flex-grow-1"
                                                                   value="Save changes" id="submit-button">
                                                        </div>
                                                    </form>
                                                    <br>
                                                    <br>
                                                    <form id="change-password-form">
                                                        <label for="current-password">Current password:</label><input
                                                                id="current-password" class="form-control"
                                                                type="password" name="current-password" maxlength="60">
                                                        <p id="current-password-error" class="invalid-feedback d-block"
                                                           role="alert"></p>
                                                        <label for="new-password">New password:</label><input
                                                                id="new-password" class="form-control" type="password"
                                                                name="new-password" maxlength="60">
                                                        <p id="new-password-error" class="invalid-feedback d-block"
                                                           role="alert"></p>
                                                        <label for="new-password-repeat">Repeat password:</label><input
                                                                id="new-password-repeat" class="form-control"
                                                                type="password" name="repeat-password" maxlength="60">
                                                        <p id="repeat-password-error" class="invalid-feedback d-block"
                                                           role="alert"></p>
                                                        <input type="button" class="btn btn-primary flex-grow-1"
                                                               value="Change password" id="change-password-button">
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</#macro>
