<#include "base.ftl">

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(function () {
            $("#post-category").change(function () {
                performCategorySearch()
            });
        });

        function performCategorySearch() {
            $('#search-box-input').val("");
            $("#nothing-found").css("display", "");
            let postCategory = $("#post-category").val();
            let amountOfPosts = 0;
            let hiddenPosts = 0;

            $(".a-post").each(function () {
                amountOfPosts++;
                let category = $(this).find(".category").text()
                if (postCategory === "All categories") {
                    $(this).css("display", "");
                } else {
                    if (category.indexOf(postCategory) === -1) {
                        $(this).css("display", "none");
                        hiddenPosts++;
                    } else {
                        $(this).css("display", "");
                    }
                }
            })
            if (hiddenPosts === amountOfPosts) {
                $("#nothing-found").css("display", "");
            } else {
                $("#nothing-found").css("display", "none");
            }
        }

        $(document).ready(function () {
            $('#search-button').click(function () {
                performTextSearch();
            });

            $('#search-box-input').keypress(function (event) {
                if (event.which === 13) {
                    event.preventDefault();
                    performTextSearch();
                }
            });

            $("#anauth-create-post").click(function () {
                alert("To create posts you should be logged in");
            });
        });

        function performTextSearch() {
            const searchTerm = $('#search-box-input').val().trim().toLowerCase();
            let amountOfPosts = 0;
            let hiddenPosts = 0;

            if (searchTerm === "") {
                performCategorySearch();
            } else {
                $(".a-post").each(function () {
                    amountOfPosts++;
                    let postName = $(this).find(".post-name").text().trim().toLowerCase();
                    if (postName.indexOf(searchTerm) === -1) {
                        $(this).css("display", "none");
                        hiddenPosts++;
                    } else {
                        if ($(this).css("display") === "none") {
                            $(this).css("display", "none");
                        } else {
                            $(this).css("display", "");
                        }
                    }
                })
                if (hiddenPosts === amountOfPosts) {
                    $("#nothing-found").css("display", "");
                } else {
                    $("#nothing-found").css("display", "none");
                }
            }
        }
    </script>
</#macro>
<#macro styles>
</#macro>

<#macro title>Favourite</#macro>

<#macro content1>
    <b>Favourite posts</b>
</#macro>
<#macro content2>
    <div class="d-flex justify-content-between">
        <div class="col-2 ms-5">
            <label for="post-category" class="mb-1">Choose your category:</label><select id="post-category"
                                                                                         name="postCategory"
                                                                                         class="form-select">
                <option selected>All categories</option>
                <option>Christianity</option>
                <option>Islam</option>
                <option>Judaism</option>
                <option>Other</option>
            </select>
        </div>
        <div class="col-5 mt-3">
            <div class="input-group">
                <input id="search-box-input" type="search" class="form-control rounded" placeholder="Search"
                       aria-label="Search" aria-describedby="search-addon"/>
                <button id="search-button" type="button" class="btn btn-outline-primary">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
        <div class="col-2 mt-3">
        </div>
    </div>
    <br>
    <br>
    <br>
    <div style="font-size:110%; text-align: center">
        <#if favouritePosts? has_content>
            <section class="gradient-custom all-posts">
                <div id="nothing-found" style="display: none">
                    <p>No results found</p>
                    <a class="btn btn-primary me-1 flex-grow-1" href="<@spring.url '/posts'/>">
                        <i class="fa fa-list-ul" aria-hidden="true"></i> Search for interesting posts
                    </a>
                </div>
                <#list favouritePosts as p>
                    <div class="container my-1 py-1 a-post">
                        <div class="row d-flex justify-content-center">
                            <div class="col-md-12 col-lg-10 col-xl-8">
                                <div class="card">
                                    <div class="card-body p-4">
                                        <div class="d-flex flex-start">
                                            <a style="text-decoration: none; color: inherit"
                                               href="<@spring.url '/another-profile?anotherUser=${p.author}'/>">
                                                <img class="rounded-circle shadow-1-strong me-3"
                                                     src="${p.authorImageUrl}" alt="avatar" width="65"
                                                     height="65"/>
                                            </a>
                                            <div class="flex-grow-1 flex-shrink-1">
                                                <div>
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <a href="<@spring.url '/post?postName=${p.name}&postAuthor=${p.author}'/>"
                                                           style="text-decoration: none; color: inherit">
                                                            <p class="post-name">
                                                                <b>${p.name}</b>
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
                                                            <p class="mb-0">${p.likes} <i
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
                </#list>
            </section>
        <#else>
            <div id="nothing">
                <p>You have no favourite posts</p>
                <a class="btn btn-primary me-1 flex-grow-1" href="<@spring.url '/posts'/>">
                    <i class="fa fa-list-ul" aria-hidden="true"></i> Search for interesting posts
                </a>
            </div>
        </#if>
    </div>
</#macro>
