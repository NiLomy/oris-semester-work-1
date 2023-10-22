<html lang="en">

<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        function handleCommentWidthChange() {
            $('.profile-content').each(function () {
                const textElement = $(this).find('.info');
                textElement.css('white-space', 'normal');
            });
        }

        $(window).on('resize', handleCommentWidthChange);

        handleCommentWidthChange();
    </script>
</#macro>
<#macro styles></#macro>

<#macro title>Profile</#macro>

<#macro content1>
    <b>This is profile of ${anotherUser.firstName} ${anotherUser.lastName}</b>
</#macro>
<#macro content2>
    <section>
        <div class="profile-content container py-5">
            <div class="row d-flex justify-content-center align-items-center">
                <div class="col col-md-9 col-lg-7 col-xl-7">
                    <div style="border-radius: 15px;">
                        <div class="p-4">
                            <div class="d-flex text-black">
                                <div class="flex-shrink-0">
                                    <img src="${anotherUser.imageUrl}"
                                         alt="Profile image" class="img-fluid"
                                         style="width: 200px; border-radius: 10px;">
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h5 class="mb-1">${anotherUser.login}</h5>
                                    <#--                                    <p class="mb-2 pb-1" style="color: #2b2a2a;">Senior Journalist</p>-->
                                    <p class="mb-2 pb-1" style="color: #2b2a2a;">Email: ${anotherUser.email}</p>
                                    <#if anotherUser.aboutMe? has_content>
                                        <div class="d-flex justify-content-start rounded-3 p-2 mb-2 text-wrap"
                                             style="background-color: #efefef;">
                                            <p class="info small text-muted mb-1 text-break text-justify">
                                                ${anotherUser.aboutMe}
                                            </p>
                                        </div>
                                    </#if>
                                    <#--                                    <div class="d-flex pt-1">-->
                                    <#--                                        <button type="button" class="btn btn-outline-primary me-1 flex-grow-1">Chat</button>-->
                                    <#--                                        <button type="button" class="btn btn-primary flex-grow-1">Follow</button>-->
                                    <#--                                    </div>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="font-size:110%; text-align: center">
                    <h3>
                        ${anotherUser.login}'s posts:
                    </h3>
                    <br>
                    <#if anotherUserPosts? has_content>
                        <#list anotherUserPosts as p>
                            <section class="gradient-custom all-posts">
                                <div class="container my-1 py-1 a-post">
                                    <div class="row d-flex justify-content-center">
                                        <div class="col-md-12 col-lg-10 col-xl-8">
                                            <div class="card" href="${pageContext}/post?postName=${p.name}&postAuthor=${p.author}">
                                                <div class="card-body p-4">
                                                    <div class="d-flex flex-start">
                                                        <img class="rounded-circle shadow-1-strong me-3"
                                                             src="${p.authorImageUrl}" alt="avatar" width="65"
                                                             height="65" />
                                                        <div class="flex-grow-1 flex-shrink-1">
                                                            <div>
                                                                <div class="d-flex justify-content-between align-items-center">
                                                                    <a href="${pageContext}/post?postName=${p.name}&postAuthor=${p.author}">
                                                                        <p class="post-name">
                                                                            ${p.name}
                                                                        </p>
                                                                    </a>
                                                                    <p class="category">
                                                                        ${p.category}
                                                                    </p>
                                                                </div>
                                                                <div class="d-flex justify-content-between align-items-center">
                                                                    <p class="mb-1">
                                                                        ${p.author} <span class="small">- ${p.date}</span>
                                                                    </p>
                                                                </div>
                                                                <div class="small d-flex justify-content-start">
                                                                    <div class="d-flex align-items-center me-3">
                                                                        <i class="far fa-thumbs-up me-2"></i>
                                                                        <p class="mb-0">${p.likes} Like</p>
                                                                    </div>
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
                    <#else>
                        <p>
                            ${anotherUser.login} didn't post anything
                        </p>
                    </#if>
                </div>
            </div>
        </div>
    </section>
</#macro>
</html>
