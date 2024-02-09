<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(function () {
            $("#remember-me").change(function () {
                let rememberMe = $("#remember-me");
                if (rememberMe.val() === "off") {
                    rememberMe.val("on");
                } else {
                    rememberMe.val("off");
                }
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

            // Password validation
            let passwordError = true;
            $("#password").keyup(function () {
                validatePassword();
            });

            function validatePassword() {
                let passwordValue = $("#password").val();
                if (passwordValue.length === 0) {
                    $("#password-error").text("You should enter your password");
                    $("#password").addClass("is-invalid");
                    passwordError = false;
                    return false;
                }
                if (passwordValue.length < 8) {
                    $("#password-error").text("Password should be at least 8 characters long");
                    $("#password").addClass("is-invalid");
                    passwordError = false;
                    return false;
                } else {
                    $("#password-error").text("");
                    $("#password").removeClass("is-invalid");
                    passwordError = true;
                }
                var regex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
                if (regex.test(passwordValue)) {
                    $("#password-error").text("")
                    $("#password").removeClass("is-invalid");
                    passwordError = true;
                } else {
                    $("#password-error").text("Too weak password. Please use letters and digits");
                    $("#password").addClass("is-invalid");
                    passwordError = false;
                }
            }

            // Confirm password validation
            let confirmPasswordError = true;
            $("#confirm-password").keyup(function () {
                validateConfirmPassword();
            });

            function validateConfirmPassword() {
                let confirmPasswordValue = $("#confirm-password").val();
                let passwordValue = $("#password").val();
                if (passwordValue !== confirmPasswordValue) {
                    $("#confirm-password-error").text("Password doesn't match");
                    $("#confirm-password").addClass("is-invalid");
                    confirmPasswordError = false;
                    return false;
                } else {
                    $("#confirm-password-error").text("");
                    $("#confirm-password").removeClass("is-invalid");
                    confirmPasswordError = true;
                }
            }

            // Submit button
            $("#submit-button").click(function () {
                validateName();
                validateLastname();
                validateEmail();
                validateNickname();
                validatePassword();
                validateConfirmPassword();

                if (
                    usernameError === true &&
                    lastnameError === true &&
                    emailError === true &&
                    nicknameError === true &&
                    passwordError === true &&
                    confirmPasswordError === true
                ) {
                    let name = $("#name").val();
                    let lastname = $("#lastname").val();
                    let email = $("#email").val();
                    let nickname = $("#nickname").val();
                    let password = $("#password").val();
                    let confirmPassword = $("#confirm-password").val();
                    let rememberMe = $("#remember-me").val();

                    $.post(
                        "${pageContext}/registration", {
                            "name": name,
                            "lastname": lastname,
                            "email": email,
                            "nickname": nickname,
                            "password": password,
                            "confirmPassword": confirmPassword,
                            "rememberMe": rememberMe,
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
                            } else if (response === "emptyPassword") {
                                $("#password-error").text("You should enter your password");
                                $("#password").addClass("is-invalid");
                            } else if (response === "shortPassword") {
                                $("#password-error").text("Password should be at least 8 characters long");
                                $("#password").addClass("is-invalid");
                            } else if (response === "weakPassword") {
                                $("#password-error").text("Too weak password. Please use letters and digits");
                                $("#password").addClass("is-invalid");
                            } else if (response === "invalidConfirmPassword") {
                                $("#confirm-password-error").text("Password doesn't match");
                                $("#confirm-password").addClass("is-invalid");
                            } else if (response === "nonUniqueEmail") {
                                $("#email-error").text("User with this email is already registered")
                                $("#email").addClass("is-invalid");
                            } else if (response === "nonUniqueNickname") {
                                $("#nickname-error").text("User with this nickname is already registered");
                                $("#nickname").addClass("is-invalid");
                            } else {
                                window.location.replace("${pageContext}/");
                            }
                        }
                    )
                }
            });
        })
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Registration</#macro>

<#macro content1>
    <div class="mb-2">
        <b>Register in</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <section class="gradient-custom">
            <div class="container my-1 py-1">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-10 col-lg-8 col-xl-4">
                        <div class="card">
                            <div class="card-body p-4" style="text-align: left">
                                <label for="name">Name: </label><input type="text" name="name" class="form-control"
                                                                       id="name" placeholder="Name" required>
                                <p id="name-error" class="invalid-feedback d-block" role="alert"></p>
                                <label for="lastname">Lastname: </label><input type="text" name="lastname"
                                                                               class="form-control" id="lastname"
                                                                               placeholder="Lastname" required>
                                <p id="lastname-error" class="invalid-feedback d-block" role="alert"></p>
                                <label for="email">Email: </label><input type="email" name="email" class="form-control"
                                                                         id="email" placeholder="Email@example.com"
                                                                         required>
                                <p id="email-error" class="invalid-feedback d-block" role="alert"></p>
                                <label for="nickname">Nickname:</label><input type="text" name="nickname"
                                                                              class="form-control" id="nickname"
                                                                              placeholder="Nickname" required>
                                <p id="nickname-error" class="invalid-feedback d-block" role="alert"></p>
                                <label for="password">Password:</label><input type="password" name="password"
                                                                              class="form-control" id="password"
                                                                              placeholder="Password" required>
                                <p id="password-error" class="invalid-feedback d-block" role="alert"></p>
                                <label for="confirm-password">Repeat password:</label><input type="password"
                                                                                             name="confirm-password"
                                                                                             class="form-control"
                                                                                             id="confirm-password"
                                                                                             placeholder="Confirm password"
                                                                                             required>
                                <p id="confirm-password-error" class="invalid-feedback d-block" role="alert"></p>
                                <div style="text-align: center">
                                    <input id="remember-me" class="form-check-input" type="checkbox" value="off"
                                           name="remember_me">
                                    <label class="form-check-label" for="remember_me">Remember me</label>
                                    <br>
                                    <br>
                                    <input id="submit-button" class="form-control" type="submit" value="REGISTER"/>
                                </div>
                            </div>
                            <hr>
                            <div class="justify-content-lg-start">
                                Already have account? <a href="${pageContext}/login">Sign in</a>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</#macro>
