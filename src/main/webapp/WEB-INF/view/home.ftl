<#include "base.ftl">

<#macro scripts></#macro>
<#macro styles></#macro>
<#macro title>Home</#macro>

<#macro content1>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 text" style="font-family:MV BOLI; font-size:200%;">
                Religious studying
            </div>
        </div>
    </div>
</#macro>
<#macro content2>
    <div class="top-content">
        <div class="container">
            <div class="row">
                <div class="col-md-8 offset-md-2 text">
                    <p style="text-indent: 30px">
                        Welcome, we are glad to welcome you to our website! Here you can study different religions,
                        communicate with like-minded people and explore the world together with our magnificent
                        community.
                        Please be polite and follow the rules of communication.
                    </p>
                    <p style="text-indent: 30px">
                        Posts on topics can be found here:
                        <a class="btn btn-primary" style="font-size: small; text-indent: 0" href="${pageContext}/posts">
                            <i class="fa fa-list-ul" aria-hidden="true"></i> Posts
                        </a>
                    </p>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-4">
                    <#if mostPopularPost? has_content>
                        <h5 class="ms-5">
                            Most popular post:
                        </h5>
                        <div class="container my-1 py-1 a-post">
                            <div class="row d-flex justify-content-center">
                                <div>
                                    <div class="card">
                                        <div class="card-body p-4">
                                            <div class="d-flex flex-start">
                                                <img class="rounded-circle shadow-1-strong me-3"
                                                     src="${mostPopularPost.authorImageUrl}" alt="avatar" width="65"
                                                     height="65"/>
                                                <div class="flex-grow-1 flex-shrink-1">
                                                    <div>
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <a href="${pageContext}/post?postName=${mostPopularPost.name}&postAuthor=${mostPopularPost.author}"
                                                               style="text-decoration: none; color: inherit">
                                                                <p class="post-name">
                                                                    <b>${mostPopularPost.name}</b>
                                                                </p>
                                                            </a>
                                                            <p class="category">
                                                                ${mostPopularPost.category}
                                                            </p>
                                                        </div>
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <p class="mb-1">
                                                                ${mostPopularPost.author} <span
                                                                        class="small">- ${mostPopularPost.date}</span>
                                                            </p>
                                                        </div>
                                                        <div class="small d-flex justify-content-start">
                                                            <div class="d-flex align-items-center me-3">
                                                                <p class="mb-0">${mostPopularPost.likes} <i
                                                                            class="far fa-thumbs-up me-2"></i></p>
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
                    </#if>
                </div>
                <div class="col-4">
                    <#if mostActiveUser? has_content>
                        <h5 class="ms-5">
                            Most active user:
                        </h5>
                        <section>
                            <div class="profile-content container">
                                <div class="row d-flex justify-content-center align-items-center">
                                    <div class="col col-md-9 col-lg-7 col-xl-7">
                                        <div style="border-radius: 15px;">
                                            <div class="p-4">
                                                <div class="d-flex text-black">
                                                    <div class="flex-shrink-0">
                                                        <a style="text-decoration: none; color: inherit"
                                                           href="${pageContext}/another-profile?anotherUser=${mostActiveUser.login}">
                                                            <img src="${mostActiveUser.imageUrl}"
                                                                 alt="Profile image" class="img-fluid"
                                                                 style="width: 120px; border-radius: 10px;">
                                                        </a>
                                                    </div>
                                                    <div class="flex-grow-1 ms-3 mt-4">
                                                        <h5 class="mb-1"><a
                                                                    href="${pageContext}/another-profile?anotherUser=${mostActiveUser.login}"
                                                                    style="text-decoration: none; color: inherit">${mostActiveUser.login}</a>
                                                        </h5>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</#macro>
