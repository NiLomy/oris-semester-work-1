<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(document).ready(function () {

            // Post name validation
            let postNameError = true;
            $("#post-name").keyup(function () {
                validatePostName();
            });

            function validatePostName() {
                let nameValue = $("#post-name").val().trim();
                if (nameValue.length === 0) {
                    $("#post-name-error").text("You should enter post name");
                    $("#post-name").addClass("is-invalid");
                    postNameError = false;
                    return false;
                } else if (nameValue.length > 100) {
                    $("#post-name-error").text("Too long post name");
                    $("#post-name").addClass("is-invalid");
                    postNameError = false;
                    return false;
                } else if (nameValue.length < 5) {
                    $("#post-name-error").text("Too short post name");
                    $("#post-name").addClass("is-invalid");
                    postNameError = false;
                    return false;
                } else {
                    $("#post-name-error").text("");
                    $("#post-name").removeClass("is-invalid");
                    postNameError = true;
                }
            }

            // Post category validation
            let postCategoryError = true;
            $("#post-category").keyup(function () {
                validatePostCategory();
            });

            function validatePostCategory() {
                let nameValue = $("#post-category").val().trim();
                if (nameValue.length === 0) {
                    $("#post-category-error").text("You should choose post category");
                    $("#post-category").addClass("is-invalid");
                    postCategoryError = false;
                    return false;
                } else {
                    $("#post-category-error").text("");
                    $("#post-category").removeClass("is-invalid");
                    postCategoryError = true;
                }
            }

            // Post text validation
            let postTextError = true;
            $("#post-text").keyup(function () {
                validatePostText();
            });

            function validatePostText() {
                let nameValue = $("#post-text").val().trim();
                if (nameValue.length === 0) {
                    $("#post-text-error").text("You should enter post text");
                    $("#post-text").addClass("is-invalid");
                    postTextError = false;
                    return false;
                } else {
                    $("#post-text-error").text("");
                    $("#post-text").removeClass("is-invalid");
                    postTextError = true;
                }
            }

            // Submit button
            $("#submit-button").click(function () {
                validatePostName();
                validatePostCategory();
                validatePostText();

                if (
                    postNameError === true &&
                    postCategoryError === true &&
                    postTextError === true
                ) {
                    let postName = $("#post-name").val();
                    let postCategory = $("#post-category").val();
                    let postText = $("#post-text").val();

                    $.post(
                        "${pageContext}/create-post", {
                            "postName": postName,
                            "postCategory": postCategory,
                            "postText": postText
                        }, function (response) {
                            if (response === "emptyPostName") {
                                $("#post-name-error").text("You should enter post name");
                                $("#post-name").addClass("is-invalid");
                            } else if (response === "shortPostName") {
                                $("#name-error").text("Too short post name");
                                $("#name").addClass("is-invalid");
                            } else if (response === "longPostName") {
                                $("#name-error").text("Too long post name");
                                $("#name").addClass("is-invalid");
                            } else if (response === "emptyPostCategory") {
                                $("#post-category-error").text("You should choose post category");
                                $("#post-category").addClass("is-invalid");
                            } else if (response === "emptyPostText") {
                                $("#post-text-error").text("You should enter post text");
                                $("#post-text").addClass("is-invalid");
                            } else {
                                window.location.replace("${pageContext}/post?postName=" + postName + "&postAuthor=" + "${currentUser.login}");
                            }
                        }
                    )
                }
            })
        })
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Create Post</#macro>

<#macro content1>
    <div style="font-size:150%; text-align:center">
        <b>Create your post</b>
    </div>
</#macro>
<#macro content2>
    <div style="font-size:110%; text-align: center">
        <section class="gradient-custom">
            <div class="container my-1 py-1">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-10 col-lg-8 col-xl-7">
                        <div class="card">
                            <div class="card-body p-4" style="text-align: left" >
                                <label class="form-floating" for="post-name">Post name: </label><input id="post-name" class="form-control" type="text" name="postName" maxlength="100">
                                <p id="post-name-error" class="invalid-feedback d-block" role="alert"></p>
                                <label class="form-floating" for="post-category">Choose your category: </label><select id="post-category" class="form-select" name="postCategory">
                                    <option selected disabled value="">Choose one of this categories</option>
                                    <option>Christianity</option>
                                    <option>Islam</option>
                                    <option>Judaism</option>
                                    <option>Other</option>
                                </select>
                                <p id="post-category-error" class="invalid-feedback d-block" role="alert"></p>
                                <label class="form-floating" for="post-text">Your post text: </label><textarea id="post-text" class="form-control" name="postText" type="text"></textarea>
                                <p id="post-text-error" class="invalid-feedback d-block" role="alert"></p>

                                <input id="submit-button" class="btn btn-primary form-control" type="submit" value="Create post"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</#macro>
