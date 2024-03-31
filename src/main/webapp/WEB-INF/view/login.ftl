<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
<#--    <script>-->

<#--        $(document).on(-->
<#--            "click", "#submit-button", function () {-->
<#--                let login = $("#login").val();-->
<#--                let password = $("#password").val();-->

<#--                $.ajax({-->
<#--                    type: "POST",-->
<#--                    url: "/login",-->
<#--                    data: {-->
<#--                        "login": login,-->
<#--                        "password": password-->
<#--                    },-->
<#--                    statusCode: {-->
<#--                        200: function () {-->
<#--                            window.location.replace("/")-->
<#--                        }-->
<#--                    }-->
<#--                });-->

<#--                // $.post(-->
<#--                //     "/login/process", {-->
<#--                //         "login": login,-->
<#--                //         "password": password-->
<#--                //     }, function (response) {-->
<#--                //         if (response === "invalidInput") {-->
<#--                //             $("#edit-status").text("Incorrect login or password");-->
<#--                //             $("#login").addClass("is-invalid");-->
<#--                //             $("#password").addClass("is-invalid");-->
<#--                //         } else {-->
<#--                //             window.location.replace("/")-->
<#--                //         }-->
<#--                //     }-->
<#--                // )-->
<#--            }-->
<#--        )-->
<#--    </script>-->
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
                                <form action="<@spring.url '/login'/>" method="post">
                                    Login:
                                    <input type="text" name="login" id="login"/>
                                    <br>
                                    Password:
                                    <input type="password" name="password" id="password"/>
                                    <br>
                                    <input type="submit" value="press me please">
                                </form>
<#--                                <label for="login">Login:</label><input type="text" name="login"-->
<#--                                                                              class="form-control" id="login"-->
<#--                                                                              placeholder="Login">-->
<#--                                <label for="password">Password:</label><input type="password" name="password"-->
<#--                                                                              class="form-control" id="password"-->
<#--                                                                              placeholder="Password">-->
<#--                                <p id="input-error" class="invalid-feedback d-block" role="alert"></p>-->

<#--                                <div style="text-align: center">-->
<#--&lt;#&ndash;                                    <div class="mt-4">&ndash;&gt;-->
<#--&lt;#&ndash;                                        <input id="remember-me" class="form-check-input" type="checkbox" value="off"&ndash;&gt;-->
<#--&lt;#&ndash;                                               name="remember_me">&ndash;&gt;-->
<#--&lt;#&ndash;                                        <label class="form-check-label" for="remember_me">Remember me</label>&ndash;&gt;-->
<#--&lt;#&ndash;                                    </div>&ndash;&gt;-->
<#--                                    <br>-->
<#--                                    <input id="submit-button" class="form-control" type="submit" value="LOGIN"/>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                            <hr>-->
<#--                            <div class="justify-content-lg-start">-->
<#--                                New here? <a href="/registration">Sign up</a>-->
<#--                            </div>-->
<#--                            <br>-->
<#--                            <div id="edit-status"></div>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</#macro>
