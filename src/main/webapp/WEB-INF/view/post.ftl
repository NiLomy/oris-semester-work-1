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
                        $("#post-like-value").text(response);
                    }
                )
            }
        )

        $(document).on(
            "click", "#unauth-like-button", function () {
                alert("To like posts you should be logged in");
            }
        )

        $(document).ready(function () {
            $(".message-like-button").click(function () {
                const messageId = $(this).data("message-id");

                $.post(
                    "${pageContext}/post", {
                        "action": 'pressMessageLike',
                        "messageId": messageId
                    }, function (response) {
                        $("#message-like-value-" + messageId).text(response);
                    }
                )
            })

            $(".unauth-message-like-button").click(function () {
                alert("To like comments you should be logged in");
            })
        })

        $(document).on(
            "click", "#unauth-favourite", function () {
                alert("To mark posts you should be logged in");
            }
        )

        let unfavouriteClickCount = 0;
        $(document).on(
            "click", "#unfavourite", function () {
                if (unfavouriteClickCount % 2 === 0) {
                    $.post(
                        "${pageContext}/post", {
                            "action": 'pressUnfavourite'
                        }, function (response) {
                            $("#unfavourite").html('<i class="fa fa-bookmark" aria-hidden="true"></i> Mark favourite');
                        }
                    )
                } else {
                    $.post(
                        "${pageContext}/post", {
                            "action": 'pressFavourite'
                        }, function (response) {
                            $("#unfavourite").html('<i class="fa fa-bookmark-o" aria-hidden="true"></i> Remove from favourites');
                        }
                    )
                }
                unfavouriteClickCount++;
            }
        )

        let favouriteClickCount = 0;
        $(document).on(
            "click", "#favourite", function () {
                let clickCount = 0;
                if (favouriteClickCount % 2 === 0) {
                    $.post(
                        "${pageContext}/post", {
                            "action": 'pressFavourite'
                        }, function (response) {
                            $("#favourite").html('<i class="fa fa-bookmark-o" aria-hidden="true"></i> Remove from favourites');
                        }
                    )
                } else {
                    $.post(
                        "${pageContext}/post", {
                            "action": 'pressUnfavourite'
                        }, function (response) {
                            $("#favourite").html('<i class="fa fa-bookmark" aria-hidden="true"></i> Mark favourite');
                        }
                    )
                }
                favouriteClickCount++;
            }
        )

        $(document).on(
            "click", "#send-message-button", function () {
                let newMessage = $("#new-message").val();
                if (newMessage !== null && newMessage.trim() !== "") {
                    $.post(
                        "${pageContext}/post", {
                            "action": "sendMessage",
                            "newMessage": newMessage
                        }, function (response) {
                            $("#messages").append(
                                '<section class="gradient-custom"><div class="container my-1 py-1"><div class="row d-flex justify-content-center"><div class="col-md-12 col-lg-10 col-xl-8"><div class="card message-card" style="border: 1px solid"><div class="card-body p-4"><div class="d-flex flex-start"><a style="text-decoration: none; color: inherit" href="${pageContext}/profile"> <img class="rounded-circle shadow-1-strong me-3"src="' + response.authorImgUrl + '" alt="avatar" width="65"height="65" /></a><div class="flex-grow-1 flex-shrink-1"><div class="d-flex justify-content-between align-items-center"><a style="text-decoration: none; color: inherit" href="${pageContext}/profile"><p class="mb-1">' + response.author + '</p></a><div><span class="small">' + response.date + '</span></div></div><div class="d-flex justify-content-between text-wrap"><p id="user-message" class="small mb-0 message text-break text-justify m-1" style="color: #000;">' + response.messageContent + '</p></div><div class="small d-flex justify-content-start"><div class="d-flex align-items-center me-3"><p id="message-like-' + response.messageID + '" class="mb-0"> <span id="message-like-value-' + response.messageID + '">' + response.likes + '</span> <a id="message-like-button-' + response.messageID + '" class="message-like-button" style="cursor: pointer; text-decoration: none; color: inherit"  data-message-id="' + response.messageID + '"><i class="far fa-thumbs-up me-2"></i></a></p></div></div></div></div></div></div></div></div></div></section>'
                            )
                            $("#new-message").val('');
                        }
                    )
                }
            }
        )

        function handleCommentWidthChange() {
            $('.message-card').each(function () {
                const textElement = $(this).find('.message');
                textElement.css('white-space', 'normal');
            });
        }

        $(window).on('resize', handleCommentWidthChange);

        handleCommentWidthChange();
    </script>
</#macro>
<#macro styles>
</#macro>

<#macro title>${currentPost.name}</#macro>

<#macro content1>
</#macro>
<#macro content2>
    <section>
        <div class="container my-2 py-2">
            <div class="row d-flex justify-content-center">
                <div class="col-md-12 col-lg-10 col-xl-8">
                    <div class="card message-card">
                        <div class="card-body">
                            <div class="d-flex flex-start align-items-center">
                                <a style="text-decoration: none; color: inherit" href="${pageContext}/another-profile?anotherUser=${currentPost.author}">
                                    <img class="rounded-circle shadow-1-strong me-3"
                                         src="${currentPost.authorImageUrl}" alt="avatar" width="60"
                                         height="60" />
                                </a>
                                <div>
                                    <a style="text-decoration: none; color: inherit" href="${pageContext}/another-profile?anotherUser=${currentPost.author}">
                                        <h6 class="fw-bold text-primary mb-1 me-5">${currentPost.author}</h6>
                                    </a>
                                </div>
                                <div>
                                    <h2 class="fw-bold text-primary mb-1 ms-5">${currentPost.name}</h2>
                                    <p class="text-muted small mb-0 ms-5">
                                        ${currentPost.date}
                                    </p>
                                </div>
                            </div>
                            <p style="text-indent: 30px" class="message mt-3 mb-4 pb-2">
                                ${currentPost.content}
                            </p>
                            <div class="d-flex justify-content-between" style="font-size: large">
                                <#if currentUser? has_content>
                                    <p id="likes"><span id="post-like-value">${currentPost.likes}</span> <a id="like-button" style="cursor: pointer; text-decoration: none; color: inherit"><i class="far fa-thumbs-up me-2"></i></a></p>
                                <#else>
                                    <p id="unauth-likes">${currentPost.likes} <a id="unauth-like-button" style="cursor: pointer; text-decoration: none; color: inherit"><i class="far fa-thumbs-up me-2"></i></a></p>
                                </#if>
                                <div>
                                    <#if currentUser? has_content>
                                        <#if isFavourite? has_content>
                                            <button type="button" id="unfavourite" class="btn btn-primary me-1 flex-grow-1"><i class="fa fa-bookmark-o" aria-hidden="true"></i> Remove from favourites</button>
                                        <#else>
                                            <button type="button" id="favourite" class="btn btn-primary me-1 flex-grow-1"><i class="fa fa-bookmark" aria-hidden="true"></i> Mark favourite</button>
                                        </#if>
                                    <#else>
                                        <button type="button" id="unauth-favourite" class="btn btn-primary me-1 flex-grow-1"><i class="fa fa-bookmark-o" aria-hidden="true"></i> Mark favourite</button>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <br>
    <div id="messages">
        <div>
            <#if messages? has_content>
                <#list messages as message>
                    <section class="gradient-custom">
                        <div class="container my-1 py-1">
                            <div class="row d-flex justify-content-center">
                                <div class="col-md-12 col-lg-10 col-xl-8">
                                    <div class="card message-card"  style="border: 1px solid">
                                        <div class="card-body p-4">
                                            <div class="d-flex flex-start">
                                                <a style="text-decoration: none; color: inherit" href="${pageContext}/another-profile?anotherUser=${message.author}">
                                                    <img class="rounded-circle shadow-1-strong me-3"
                                                         src="${message.authorImgUrl}" alt="avatar" width="65"
                                                         height="65" />
                                                </a>
                                                <div class="flex-grow-1 flex-shrink-1">
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <a style="text-decoration: none; color: inherit" href="${pageContext}/another-profile?anotherUser=${message.author}">
                                                            <p class="mb-1">
                                                                ${message.author}
                                                            </p>
                                                        </a>
                                                        <div><span class="small">${message.date}</span></div>
                                                    </div>
                                                    <div class="d-flex justify-content-between text-wrap">
                                                        <p id="user-message" class="small mb-0 message text-break text-justify m-1"
                                                           style="color: #000;">${message.content}</p>
                                                    </div>
                                                    <div class="small d-flex justify-content-start">
                                                        <div class="d-flex align-items-center me-3">
                                                            <#if currentUser? has_content>
                                                                <p id="message-like-${message.id}" class="mb-0"><span id="message-like-value-${message.id}">${message.likes}</span> <a id="message-like-button-${message.id}" class="message-like-button" style="cursor: pointer; text-decoration: none; color: inherit"  data-message-id="${message.id}"><i class="far fa-thumbs-up me-2"></i></a></p>
                                                            <#else>
                                                                <p id="unauth-message-like-${message.id}" class="mb-0">${message.likes} <a id="unauth-message-like-button" class="unauth-message-like-button" style="cursor: pointer; text-decoration: none; color: inherit"  data-message-id="${message.id}"><i class="far fa-thumbs-up me-2"></i></a></p>
                                                            </#if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </#list>
            </#if>
        </div>
    </div>
    <br>
    <div>
        <section>
            <div class="container my-1 py-1">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-12 col-lg-10 col-xl-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="flex-start align-items-center">
                                    <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
                                        <#if currentUser? has_content>
                                            <div class="d-flex flex-start w-100">
                                                <img class="rounded-circle shadow-1-strong me-3"
                                                     src="${currentUser.imageUrl}" alt="avatar" width="40"
                                                     height="40" />
                                                <div class="w-100 form-floating">
                                                    <textarea id="new-message" placeholder="Type comment" class="form-control" rows="4" style="background: #fff;"></textarea>
                                                    <label class="form-floating" for="new-message">Message</label>
                                                    <p id="new-message-error" class="invalid-feedback d-block" role="alert"></p>
                                                </div>
                                            </div>
                                            <div class="float-end mt-2 pt-1">
                                                <button id="send-message-button" type="button" class="btn btn-primary btn-sm">Send comment</button>
                                            </div>
                                        <#else>
                                            <div class="w-100 form-floating" style="text-align: center">
                                                <p id="anon-user-message" class="form-control">To write comments you should be logged in</p>
                                                <br>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</#macro>
