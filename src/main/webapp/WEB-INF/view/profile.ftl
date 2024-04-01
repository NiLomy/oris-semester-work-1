<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles>
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

<#macro title>Profile</#macro>

<#macro content1>
    <b>This is your profile, ${currentUser.firstName} ${currentUser.lastName}</b>
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
                                    <img src="${currentUser.imageUrl}"
                                         alt="Profile image" class="img-fluid"
                                         style="width: 200px; border-radius: 10px;">
                                    <div class="d-flex pt-1">
                                        <a href="<@spring.url '/edit-profile'/>" type="button"
                                           class="btn btn-primary flex-grow-1">Edit profile</a>
                                    </div>
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h5 class="mb-1">${currentUser.login}</h5>
                                    <p class="mb-2 pb-1" style="color: #2b2a2a;">Email: ${currentUser.email}</p>
                                    <#if currentUser.aboutMe? has_content>
                                        <div class="d-flex justify-content-start rounded-3 p-2 mb-2 text-wrap"
                                             style="background-color: #efefef;">
                                            <p class="info small text-muted mb-1 text-break text-justify">
                                                ${currentUser.aboutMe}
                                            </p>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="font-size:110%; text-align: center">
                    <h3>
                        Your posts:
                    </h3>
                    <br>
                    <#if currentUserPosts? has_content>
                        <#list currentUserPosts as p>
                            <section class="gradient-custom all-posts">
                                <div class="container my-1 py-1 a-post">
                                    <div class="row d-flex justify-content-center">
                                        <div class="col-md-12 col-lg-10 col-xl-8">
                                            <div class="card"
                                                 href="<@spring.url '/post?postName=${p.name}&postAuthor=${p.author}'/>">
                                                <div class="card-body p-4">
                                                    <div class="d-flex flex-start">
                                                        <img class="rounded-circle shadow-1-strong me-3"
                                                             src="${p.authorImageUrl}" alt="avatar" width="65"
                                                             height="65"/>
                                                        <div class="flex-grow-1 flex-shrink-1">
                                                            <div>
                                                                <div class="d-flex justify-content-between align-items-center">
                                                                    <a href="<@spring.url '/post?postName=${p.name}&postAuthor=${p.author}'/>">
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
                                                                        ${p.author} <span
                                                                                class="small">- ${p.date}</span>
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
                            You don't have posts =(
                        </p>
                        <p>
                            Fix it by <a class="btn btn-outline-primary me-1 flex-grow-1"
                                         href="<@spring.url '/create-post'/>">creating post</a>
                        </p>
                    </#if>
                </div>
            </div>
        </div>
    </section>
</#macro>
