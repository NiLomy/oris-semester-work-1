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

        $(document).on(
            "click", "#submit-button", function () {
                let login = $("#login").val();
                let password = $("#password").val();
                let rememberMe = $("#remember-me").val();

                $.post(
                    "<@spring.url '/login'/>", {
                        "login": login,
                        "password": password,
                        "rememberMe": rememberMe
                    }, function (response) {
                        if (response === "invalidInput") {
                            $("#edit-status").text("Incorrect login or password");
                            $("#login").addClass("is-invalid");
                            $("#password").addClass("is-invalid");
                        } else {
                            window.location.replace("<@spring.url '/'/>")
                        }
                    }
                )
            }
        )
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Login</#macro>

<#macro content1>
    <b>Please log in</b>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <section class="gradient-custom">
            <div class="container my-1 py-1">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-10 col-lg-8 col-xl-4">
                        <div class="card">
                            <div class="card-body p-4" style="text-align: left">
                                <label for="login">Login:</label><input type="text" name="login"
                                                                              class="form-control" id="login"
                                                                              placeholder="Login">
                                <label for="password">Password:</label><input type="password" name="password"
                                                                              class="form-control" id="password"
                                                                              placeholder="Password">
                                <p id="input-error" class="invalid-feedback d-block" role="alert"></p>

                                <div style="text-align: center">
                                    <div class="mt-4">
                                        <input id="remember-me" class="form-check-input" type="checkbox" value="off"
                                               name="remember_me">
                                        <label class="form-check-label" for="remember_me">Remember me</label>
                                    </div>
                                    <br>
                                    <input id="submit-button" class="form-control" type="submit" value="LOGIN"/>
                                </div>
                            </div>
                            <hr>
                            <div class="justify-content-lg-start">
                                New here? <a href="<@spring.url '/registration'/>">Sign up</a>
                            </div>
                            <br>
                            <div id="edit-status"></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</#macro>
