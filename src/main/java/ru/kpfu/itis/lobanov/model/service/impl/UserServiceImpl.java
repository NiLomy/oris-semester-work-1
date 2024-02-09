package ru.kpfu.itis.lobanov.model.service.impl;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.CloudinaryUtil;
import ru.kpfu.itis.lobanov.util.PasswordCryptographer;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    public static final String REQUEST_TYPE_FILE = "file";
    public static final String CLOUDINARY_URL_KEY = "url";

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto get(int id) {
        User user = userDao.get(id);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto get(String nickname) {
        User user = userDao.get(nickname);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto get(String login, String password) {
        User user = userDao.get(login, PasswordCryptographer.encrypt(password));
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(
                u -> new UserDto(u.getName(), u.getLastname(), u.getLogin(), u.getEmail(), u.getAboutMe())
        ).collect(Collectors.toList());
    }

    @Override
    public String getEmail(String login, String password) {
        return userDao.getEmail(login, password);
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String isRemembered = req.getParameter("remember_me");

        resp.setContentType("text/plain");

        if (name.trim().isEmpty()) {
            resp.getWriter().write("emptyName");
            return;
        }
        if (name.trim().length() > 60) {
            resp.getWriter().write("longName");
            return;
        }
        if (lastname.trim().isEmpty()) {
            resp.getWriter().write("emptyLastname");
            return;
        }
        if (lastname.trim().length() > 60) {
            resp.getWriter().write("longLastname");
            return;
        }
        if (email.isEmpty()) {
            resp.getWriter().write("emptyEmail");
            return;
        }
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            resp.getWriter().write("invalidEmail");
            return;
        }
        if (nickname.trim().isEmpty()) {
            resp.getWriter().write("emptyNickname");
            return;
        }
        if (nickname.trim().length() < 5) {
            resp.getWriter().write("shortNickname");
            return;
        }
        if (nickname.trim().length() > 60) {
            resp.getWriter().write("longNickname");
            return;
        }
        if (password.isEmpty()) {
            resp.getWriter().write("emptyPassword");
            return;
        }
        if (password.length() < 8) {
            resp.getWriter().write("shortPassword");
            return;
        }
        if (!password.matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            resp.getWriter().write("weakPassword");
            return;
        }
        if (!confirmPassword.equals(password)) {
            resp.getWriter().write("invalidConfirmPassword");
            return;
        }
        if (!isEmailUnique(email)) {
            resp.getWriter().write("nonUniqueEmail");
            return;
        }
        if (!isNicknameUnique(nickname)) {
            resp.getWriter().write("nonUniqueNickname");
            return;
        }

        User user = new User(
                name, lastname, email, nickname, PasswordCryptographer.encrypt(password), null
        );
        userDao.save(user);

        UserDto currentUser = get(nickname);
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(ServerResources.CURRENT_USER, currentUser);

        if (isRemembered != null && isRemembered.equals(ServerResources.IS_REMEMBER_ME_PRESSED)) {
            remember(currentUser, req, resp);
        }
    }

    @Override
    public void updateInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        String aboutMe = req.getParameter("aboutMe");
        String emptyAboutMe = req.getParameter("emptyAboutMe");

        HttpSession httpSession = req.getSession();
        UserDto currentUserDto = (UserDto) httpSession.getAttribute("currentUser");

        if (name.trim().isEmpty()) {
            resp.getWriter().write("emptyName");
            return;
        }
        if (name.trim().length() > 60) {
            resp.getWriter().write("longName");
            return;
        }
        if (lastname.trim().isEmpty()) {
            resp.getWriter().write("emptyLastname");
            return;
        }
        if (lastname.trim().length() > 60) {
            resp.getWriter().write("longLastname");
            return;
        }
        if (nickname.trim().isEmpty()) {
            resp.getWriter().write("emptyNickname");
            return;
        }
        if (!nickname.equals(currentUserDto.getLogin())) {
            if (nickname.trim().length() < 5) {
                resp.getWriter().write("shortNickname");
                return;
            }
            if (nickname.trim().length() > 60) {
                resp.getWriter().write("longNickname");
                return;
            }
            if (!isNicknameUnique(nickname)) {
                resp.getWriter().write("nonUniqueNickname");
                return;
            }
        }
        if (email.isEmpty()) {
            resp.getWriter().write("emptyEmail");
            return;
        }
        if (!email.equals(currentUserDto.getEmail())) {
            if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                resp.getWriter().write("invalidEmail");
                return;
            }
            if (!isEmailUnique(email)) {
                resp.getWriter().write("nonUniqueEmail");
                return;
            }
        }
        if (aboutMe == null && emptyAboutMe != null) {
            aboutMe = emptyAboutMe;
        }

        User user = new User(name, lastname, email, nickname, aboutMe);

        User currentUser = userDao.get(currentUserDto.getLogin());
        userDao.update(user, currentUser.getId());

        UserDto userDto = get(currentUser.getId());
        remember(userDto, req, resp);
        httpSession.setAttribute("currentUser", userDto);
        resp.getWriter().write("Your data was successfully changed!");
    }

    @Override
    public void updateImageUrl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart(REQUEST_TYPE_FILE);
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        InputStream content = part.getInputStream();

        File file = File.createTempFile(filename, null);
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];

        content.read(buffer);

        outputStream.write(buffer);
        outputStream.close();

        Map map = cloudinary.uploader().upload(file, new HashMap<>());
        String imageUrl = (String) map.get(CLOUDINARY_URL_KEY);

        HttpSession httpSession = req.getSession();
        UserDto currentUser = (UserDto) httpSession.getAttribute(ServerResources.CURRENT_USER);
        userDao.updateImageUrl(currentUser.getLogin(), imageUrl);
        UserDto userDto = get(currentUser.getLogin());
        httpSession.setAttribute(ServerResources.CURRENT_USER, userDto);

        file.deleteOnExit();
        resp.getWriter().write(imageUrl);
    }

    @Override
    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatPassword = req.getParameter("repeatPassword");

        if (!(currentPassword.isEmpty() && newPassword.isEmpty() && repeatPassword.isEmpty())) {
            HttpSession httpSession = req.getSession();
            UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");

            if (currentPassword.isEmpty()) {
                resp.getWriter().write("emptyCurrentPassword");
                return;
            }
            if (!isPasswordMatches(currentUser.getLogin(), currentPassword)) {
                resp.getWriter().write("invalidPassword");
                return;
            }
            if (newPassword.isEmpty()) {
                resp.getWriter().write("emptyNewPassword");
                return;
            }
            if (newPassword.length() < 8) {
                resp.getWriter().write("shortPassword");
                return;
            }
            if (!newPassword.matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
                resp.getWriter().write("weakPassword");
                return;
            }
            if (!repeatPassword.equals(newPassword)) {
                resp.getWriter().write("invalidConfirmPassword");
                return;
            }

            userDao.updatePassword(currentUser.getLogin(), PasswordCryptographer.encrypt(newPassword));
            resp.getWriter().write("Your password was successfully changed!");
        }
    }

    @Override
    public boolean isEmailUnique(String email) {
        return getAll().stream().noneMatch(userDto -> userDto.getEmail().equals(email));
    }

    @Override
    public boolean isNicknameUnique(String nickname) {
        return getAll().stream().noneMatch(userDto -> userDto.getLogin().equals(nickname));
    }

    @Override
    public boolean isPasswordMatches(String nickname, String password) {
        User user = userDao.get(nickname);
        return user.getPassword().equals(PasswordCryptographer.encrypt(password));
    }

    @Override
    public void remember(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("user", user.getLogin());
        cookie.setMaxAge(24 * 3_600);
        resp.addCookie(cookie);
    }

    @Override
    public void authUser(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    HttpSession httpSession = req.getSession();
                    UserDto user = get(c.getValue());
                    httpSession.setAttribute("currentUser", user);
                    break;
                }
            }
        }
    }
    // made a map of errors and send it to front
}
