package ru.kpfu.itis.lobanov.util.constants;

public class ServerResources {
    // contexts
    public static final String PAGE_CONTEXT = "pageContext";
    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_USER_POSTS = "currentUserPosts";
    public static final String CURRENT_POST = "currentPost";
    public static final String ANOTHER_USER = "anotherUser";
    public static final String ANOTHER_USER_POSTS = "anotherUserPosts";
    public static final String POST_NAME = "postName";
    public static final String POST_AUTHOR = "postAuthor";
    public static final String POST_CATEGORY = "postCategory";
    public static final String POST_TEXT = "postText";
    public static final String FAVOURITE_POSTS = "favouritePosts";
    public static final String MOST_POPULAR_POST = "mostPopularPost";
    public static final String MOST_ACTIVE_USER = "mostActiveUser";
    public static final String NICKNAME = "nickname";
    public static final String PASSWORD = "password";
    public static final String REMEMBER_ME = "rememberMe";
    public static final String MESSAGES = "messages";
    public static final String POSTS = "posts";
    public static final String USER_SERVICE = "userService";
    public static final String POST_SERVICE = "postService";
    public static final String MESSAGE_SERVICE = "messageService";
    public static final String POST_LIKE_SERVICE = "postLikeService";
    public static final String MESSAGE_LIKE_SERVICE = "messageLikeService";
    // validation
    public static final String RESP_TYPE_PLAIN_TEXT = "text/plain";
    public static final String ACTION_KEY = "action";
    public static final String UPDATE_INFO_ACTION = "updateInfo";
    public static final String UPDATE_PASSWORD_ACTION = "changePassword";
    public static final String SEND_MESSAGE_ACTION = "sendMessage";
    public static final String PRESS_LIKE_ACTION = "pressLike";
    public static final String PRESS_MESSAGE_LIKE_ACTION = "pressMessageLike";
    public static final String PRESS_FAVOURITE_ACTION = "pressFavourite";
    public static final String PRESS_UNFAVOURITE_ACTION = "pressUnfavourite";
    public static final String POST_ALREADY_EXISTS = "postAlreadyExist";
    public static final String VALID_INPUT = "validInput";
    public static final String INVALID_INPUT = "invalidInput";
    public static final String IS_REMEMBER_ME_PRESSED = "on";
    public static final String IS_FAVOURITE = "isFavourite";
    // views
    public static final String HOME_PAGE = "WEB-INF/view/home.ftl";
    public static final String LOGIN_PAGE = "WEB-INF/view/login.ftl";
    public static final String REGISTRATION_PAGE = "WEB-INF/view/registration.ftl";
    public static final String ABOUT_US_PAGE = "WEB-INF/view/about-us.ftl";
    public static final String PROFILE_PAGE = "WEB-INF/view/profile.ftl";
    public static final String ANOTHER_USER_PROFILE_PAGE = "WEB-INF/view/another-user-profile.ftl";
    public static final String CREATE_POST_PAGE = "WEB-INF/view/create-post.ftl";
    public static final String EDIT_PROFILE_PAGE = "WEB-INF/view/edit-profile.ftl";
    public static final String FAVOURITE_PAGE = "WEB-INF/view/favourite.ftl";
    public static final String POST_PAGE = "WEB-INF/view/post.ftl";
    public static final String POSTS_PAGE = "WEB-INF/view/posts.ftl";
    public static final String PRIVACY_POLICY_PAGE = "WEB-INF/view/privacy-policy.ftl";
    public static final String TERMS_AND_CONDITIONS_PAGE = "WEB-INF/view/terms-conditions.ftl";
    public static final String EXCEPTION_PAGE = "WEB-INF/view/exception.ftl";
    // servlets mapping
    public static final String HOME_URL = "";
    public static final String LOGIN_URL = "/login";
    public static final String REGISTRATION_URL = "/registration";
    public static final String POST_URL = "/post";
    public static final String POSTS_URL = "/posts";
    public static final String ABOUT_US_URL = "/about-us";
    public static final String CREATE_POST_URL = "/create-post";
    public static final String ANOTHER_PROFILE_URL = "/another-profile";
    public static final String EDIT_PROFILE_URL = "/edit-profile";
    public static final String FAVOURITE_URL = "/favourite";
    public static final String LOGOUT_URL = "/logout";
    public static final String PRIVACY_POLICY_URL = "/privacy-policy";
    public static final String PROFILE_URL = "/profile";
    public static final String TERMS_AND_CONDITIONS_URL = "/terms-conditions";
    public static final String POST_OF_AUTHOR_URL = "/post?postName=%s&postAuthor=%s";
    public static final String EXCEPTION_HANDLER_URL = "/handle";
    public static final String CHARACTER_ENCODING_FILTER_URL = "/*";
    // property keys
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USER_KEY = "db.user";
    public static final String DB_PASSWORD_KEY = "db.password";
    public static final String CLOUDINARY_NAME_KEY = "cloud_name";
    public static final String CLOUDINARY_API_KEY = "api_key";
    public static final String CLOUDINARY_API_SECRET_KEY = "api_secret";
    // cloud sizes
    public static final int MAX_FILE_SIZE = 5 * 1024 * 1024;
    public static final int MAX_REQUEST_SIZE = 10 * 1024 * 1024;

    private ServerResources() {
    }
}
