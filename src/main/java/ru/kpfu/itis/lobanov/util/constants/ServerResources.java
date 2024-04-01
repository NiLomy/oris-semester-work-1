package ru.kpfu.itis.lobanov.util.constants;

public class ServerResources {
    // contexts
    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_USER_POSTS = "currentUserPosts";
    public static final String CURRENT_POST = "currentPost";
    public static final String CURRENT_PASSWORD = "currentPassword";
    public static final String ANOTHER_USER = "anotherUser";
    public static final String ANOTHER_USER_POSTS = "anotherUserPosts";
    public static final String POST_NAME = "postName";
    public static final String POST_AUTHOR = "postAuthor";
    public static final String POST_CATEGORY = "postCategory";
    public static final String POST_TEXT = "postText";
    public static final String FAVOURITE_POSTS = "favouritePosts";
    public static final String MOST_POPULAR_POST = "mostPopularPost";
    public static final String MOST_ACTIVE_USER = "mostActiveUser";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String ABOUT_ME = "aboutMe";
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    public static final String REMEMBER_ME = "rememberMe";
    public static final String MESSAGES = "messages";
    public static final String NEW_MESSAGE = "newMessage";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String REPEAT_PASSWORD = "repeatPassword";
    public static final String MESSAGE_ID = "messageId";
    public static final String POSTS = "posts";
    public static final String USER_AUTHORITY = "USER";
    public static final String ADMIN_AUTHORITY = "ADMIN";
    public static final String SESSION_ID_KEY = "SESSIONID";
    public static final String GET_METHOD = "GET";
    public static final String ENCODING = "UTF-8";
    public static final String SUCCESS_RESPONSE = "success";
    public static final String EMPTY_POST_NAME = "emptyPostName";
    public static final String SHORT_POST_NAME = "shortPostName";
    public static final String LONG_POST_NAME = "longPostName";
    public static final String EMPTY_POST_CATEGORY = "emptyPostCategory";
    public static final String EMPTY_POST_TEXT = "emptyPostText";
    public static final String EMPTY_NAME = "emptyName";
    public static final String LONG_NAME = "longName";
    public static final String EMPTY_LASTNAME = "emptyLastname";
    public static final String LONG_LASTNAME = "longLastname";
    public static final String EMPTY_EMAIL = "emptyEmail";
    public static final String INVALID_EMAIl = "invalidEmail";
    public static final String EMPTY_LOGIN = "emptyNickname";
    public static final String SHORT_LOGIN = "shortNickname";
    public static final String LONG_LOGIN = "longNickname";
    public static final String EMPTY_PASSWORD = "emptyPassword";
    public static final String EMPTY_CURRENT_PASSWORD = "emptyCurrentPassword";
    public static final String INVALID_PASSWORD = "invalidPassword";
    public static final String EMPTY_NEW_PASSWORD = "emptyNewPassword";
    public static final String SHORT_PASSWORD = "shortPassword";
    public static final String WEAK_PASSWORD = "weakPassword";
    public static final String INVALID_CONFIRM_PASSWORD = "invalidConfirmPassword";
    public static final String NON_UNIQUE_EMAIL = "nonUniqueEmail";
    public static final String NON_UNIQUE_LOGIN = "nonUniqueNickname";
    public static final String PASSWORD_REGEX = "^(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String MESSAGE_CONTENT = "messageContent";
    public static final String ENTERED_WRONG_DATA = "You entered wrong data";
    public static final String DEFAULT_IMG_URL = "https://res.cloudinary.com/dr96a1nqv/image/upload/v1697035213/bk1136u5xdt1d6orehlq.jpg";
    public static final String TIME_CHAR = "T";
    public static final String DOT_CHAT = "\\.";
    public static final String FILE = "file";
    public static final String MULTIPART_RESOLVER_BEAN = "multipartResolver";
    public static final String INVALID_INPUT = "invalidInput";
    // spring
    public static final String VIEW_PREFIX = "";
    public static final String VIEW_SUFFIX = ".ftl";
    public static final String VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";
    public static final String VIEW_PATH = "/WEB-INF/view/";
    public static final String TRANSACTION_MANAGER = "transactionManager";
    public static final String DATABASE = "database";
    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USER = "jdbc.user";
    public static final String JDBC_PASSWORD = "jdbc.password";
    public static final String JDBC_SHOW_SQL = "jdbc.show-sql";
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String BASE_PACKAGE = "ru.kpfu.itis.lobanov";
    public static final String SECURITY_PACKAGE = "ru.kpfu.itis.lobanov.security";
    public static final String REPOSITORIES_PACKAGE = "ru.kpfu.itis.lobanov.model.repositories";
    public static final String MODEL_PACKAGE = "ru.kpfu.itis.lobanov.model";
    public static final String CONTROLLERS_PACKAGE = "ru.kpfu.itis.lobanov.controllers";
    public static final String PERSISTENCE_PROPERTIES_PATH = "classpath:/properties/persistence.properties";

    // validation
    public static final String POST_ALREADY_EXISTS = "postAlreadyExist";
    public static final String IS_FAVOURITE = "isFavourite";
    // views
    public static final String HOME_PAGE = "home";
    public static final String LOGIN_PAGE = "login";
    public static final String REGISTRATION_PAGE = "registration";
    public static final String ABOUT_US_PAGE = "about-us";
    public static final String PROFILE_PAGE = "profile";
    public static final String ANOTHER_USER_PROFILE_PAGE = "another-user-profile";
    public static final String CREATE_POST_PAGE = "create-post";
    public static final String EDIT_PROFILE_PAGE = "edit-profile";
    public static final String FAVOURITE_PAGE = "favourite";
    public static final String POST_PAGE = "post";
    public static final String POSTS_PAGE = "posts";
    public static final String PRIVACY_POLICY_PAGE = "privacy-policy";
    public static final String TERMS_AND_CONDITIONS_PAGE = "terms-conditions";
    public static final String EXCEPTION_PAGE = "exception";
    // servlets mapping
    public static final String HOME_URL = "/";
    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_FAILED_URL = "/login?error=true";
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
    public static final String EXCEPTION_HANDLER_URL = "/handle";
    public static final String SEND_MESSAGE_URL = "/send-message";
    public static final String PRESS_LIKE_URL = "/press-like";
    public static final String PRESS_MESSAGE_LIKE_URL = "/press-message-like";
    public static final String PRESS_FAVOURITE_URL = "/press-favourite";
    public static final String PRESS_UNFAVOURITE_URL = "/press-unfavourite";
    public static final String PASSWORD_URL = "/password";
    public static final String INFO_URL = "/info";
    public static final String PHOTO_URL = "/photo";
    // property keys
    public static final String CLOUDINARY_NAME_KEY = "cloud_name";
    public static final String CLOUDINARY_API_KEY = "api_key";
    public static final String CLOUDINARY_API_SECRET_KEY = "api_secret";
    // cloud sizes
    public static final int MAX_FILE_SIZE = 5 * 1024 * 1024;
    public static final int MAX_REQUEST_SIZE = 10 * 1024 * 1024;
    // check sizes
    public static final int NAME_MAX_LENGTH = 60;
    public static final int LASTNAME_MAX_LENGTH = 60;
    public static final int LOGIN_MIN_LENGTH = 5;
    public static final int LOGIN_MAX_LENGTH = 60;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int POST_NAME_MIN_LENGTH = 5;
    public static final int POST_NAME_MAX_LENGTH = 100;

    private ServerResources() {
    }
}
