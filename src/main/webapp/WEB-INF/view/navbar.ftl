<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid justify-content-between">

        <div class="d-flex me-2 mb-1">
            <div style="font-family:MV BOLI; font-size:200%;" class="me-2">
                <a href="${pageContext}" style="text-decoration: none; color: inherit">
                    Religious studying
                </a>
            </div>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext}">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext}/posts">Posts</a>
                    </li>
                </ul>
            </div>
        </div>

        <ul class="navbar-nav flex-row">
            <div class="d-flex align-items-center">
                <#if currentUser?has_content>
                    <div class="dropdown">
                        <div id="dropdownProfileImage" style="cursor: pointer" class="dropdown-toggle"
                             data-bs-toggle="dropdown" aria-expanded="false">
                            <img id="navProfileImage" src="${currentUser.imageUrl}"
                                 class="rounded-circle shadow-1-strong" height="45" width="45" alt="Profile picture"
                                 loading="lazy"/>
                            <span class="arrow"></span>
                        </div>
                        <ul id="navbar-dropdown" class="dropdown-menu dropdown-menu-end"
                            aria-labelledby="dropdownProfileImage">
                            <li style="--delay: 1;"><a class="dropdown-item" href="${pageContext}/profile">My
                                    profile</a></li>
                            <li style="--delay: 1;"><a class="dropdown-item"
                                                       href="${pageContext}/favourite">Favourite</a></li>
                            <li style="--delay: 3"><a class="dropdown-item" href="${pageContext}/logout">Logout</a></li>
                        </ul>
                    </div>
                <#else>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent2">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext}/registration">Register</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-primary me-1 flex-grow-1" href="${pageContext}/login">Log in</a>
                            </li>
                        </ul>
                    </div>
                </#if>
            </div>
        </ul>
    </div>
</nav>
