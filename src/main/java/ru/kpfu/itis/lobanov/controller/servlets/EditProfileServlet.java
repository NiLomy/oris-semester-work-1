package ru.kpfu.itis.lobanov.controller.servlets;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.CloudinaryUtil;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "editProfileServlet", urlPatterns = "/edit-profile")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class EditProfileServlet extends HttpServlet {
    private UserService userService;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/edit-profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            updatePhoto(req, resp);
        } else {
            if (action.equals("updateInfo")) updateInfo(req, resp);
            if (action.equals("changePassword")) changePassword(req, resp);
        }
    }

    private void updateInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        String aboutMe = req.getParameter("aboutMe");
        String emptyAboutMe = req.getParameter("emptyAboutMe");

        resp.setContentType("text/plain");
        HttpSession httpSession = req.getSession();
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");

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
        if (!nickname.equals(currentUser.getLogin())) {
            if (nickname.trim().length() < 5) {
                resp.getWriter().write("shortNickname");
                return;
            }
            if (nickname.trim().length() > 60) {
                resp.getWriter().write("longNickname");
                return;
            }
            if (!userService.isNicknameUnique(nickname)) {
                resp.getWriter().write("nonUniqueNickname");
                return;
            }
        }
        if (email.isEmpty()) {
            resp.getWriter().write("emptyEmail");
            return;
        }
        if (!email.equals(currentUser.getEmail())) {
            if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                resp.getWriter().write("invalidEmail");
                return;
            }
            if (!userService.isEmailUnique(email)) {
                resp.getWriter().write("nonUniqueEmail");
                return;
            }
        }
        if (aboutMe == null && emptyAboutMe != null) {
            aboutMe = emptyAboutMe;
        }

        User user = new User(name, lastname, email, nickname, aboutMe);

        if (userService.update(user, currentUser.getLogin())) {
            UserDto userDto = userService.get(nickname);
            httpSession.setAttribute("currentUser", userDto);
            userService.remember(userDto, req, resp);
            resp.getWriter().write("Your data was successfully changed!");
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatPassword = req.getParameter("repeatPassword");

        resp.setContentType("text/plain");

        if (!(currentPassword.isEmpty() && newPassword.isEmpty() && repeatPassword.isEmpty())) {
            HttpSession httpSession = req.getSession();
            UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");

            if (currentPassword.isEmpty()) {
                resp.getWriter().write("emptyCurrentPassword");
                return;
            }
            if (!userService.isPasswordMatches(currentUser.getLogin(), currentPassword)) {
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

            if (userService.updatePassword(currentUser.getLogin(), newPassword)) {
                resp.getWriter().write("Your password was successfully changed!");
            }
        }
    }

    private void updatePhoto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file" );
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

//        File file = new File(FILE_PATH_PREFIX + File.separator
//                + filename.hashCode() % DIRECTORIES_COUNT + File.separator + filename);
        InputStream content = part.getInputStream();

        File file = File.createTempFile(filename, null);
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];

        content.read(buffer);

        outputStream.write(buffer);
        outputStream.close();

        Map map = cloudinary.uploader().upload(file, new HashMap<>());
        String imageUrl = (String) map.get("url");

        HttpSession httpSession = req.getSession();
        UserDto currentUser = (UserDto) httpSession.getAttribute("currentUser");
        userService.updateImageUrl(currentUser.getLogin(), imageUrl);
        UserDto userDto = userService.get(currentUser.getLogin());
        httpSession.setAttribute("currentUser", userDto);

        file.deleteOnExit();
        resp.setContentType("text/plain");
        resp.getWriter().write(imageUrl);
    }
}
