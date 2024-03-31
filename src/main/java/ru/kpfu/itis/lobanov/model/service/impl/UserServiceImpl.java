package ru.kpfu.itis.lobanov.model.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.Role;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.repositories.RoleRepository;
import ru.kpfu.itis.lobanov.model.repositories.UserRepository;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.PasswordCryptographer;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Cloudinary cloudinary;
    private final PasswordCryptographer passwordCryptographer;
    private final PasswordEncoder passwordEncoder;
    public static final String REQUEST_TYPE_FILE = "file";
    public static final String CLOUDINARY_URL_KEY = "url";

    @Override
    public UserDto get(int id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto get(String nickname) {
        User user = userRepository.findByLogin(nickname);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto get(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login, passwordCryptographer.encrypt(password));
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto getByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, passwordCryptographer.encrypt(password));
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(
                u -> new UserDto(u.getName(), u.getLastname(), u.getLogin(), u.getEmail(), u.getAboutMe())
        ).collect(Collectors.toList());
    }

    @Override
    public String getEmail(String login, String password) {
        return userRepository.findEmailByLoginAndPassword(login, password);
    }

    @Override
    public UserDto save(String name, String lastname, String email, String nickname, String password, String confirmPassword, String isRemembered, HttpServletResponse resp) throws ServletException, IOException {
        if (name.trim().isEmpty()) {
            resp.getWriter().write("emptyName");
            return null;
        }
        if (name.trim().length() > 60) {
            resp.getWriter().write("longName");
            return null;
        }
        if (lastname.trim().isEmpty()) {
            resp.getWriter().write("emptyLastname");
            return null;
        }
        if (lastname.trim().length() > 60) {
            resp.getWriter().write("longLastname");
            return null;
        }
        if (email.isEmpty()) {
            resp.getWriter().write("emptyEmail");
            return null;
        }
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            resp.getWriter().write("invalidEmail");
            return null;
        }
        if (nickname.trim().isEmpty()) {
            resp.getWriter().write("emptyNickname");
            return null;
        }
        if (nickname.trim().length() < 5) {
            resp.getWriter().write("shortNickname");
            return null;
        }
        if (nickname.trim().length() > 60) {
            resp.getWriter().write("longNickname");
            return null;
        }
        if (password.isEmpty()) {
            resp.getWriter().write("emptyPassword");
            return null;
        }
        if (password.length() < 8) {
            resp.getWriter().write("shortPassword");
            return null;
        }
        if (!password.matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            resp.getWriter().write("weakPassword");
            return null;
        }
        if (!confirmPassword.equals(password)) {
            resp.getWriter().write("invalidConfirmPassword");
            return null;
        }
        if (!isEmailUnique(email)) {
            resp.getWriter().write("nonUniqueEmail");
            return null;
        }
        if (!isNicknameUnique(nickname)) {
            resp.getWriter().write("nonUniqueNickname");
            return null;
        }

//        User user = new User(
//                name, lastname, email, nickname, passwordCryptographer.encrypt(password), "https://res.cloudinary.com/dr96a1nqv/image/upload/v1697035213/bk1136u5xdt1d6orehlq.jpg", null
//        );
        User user = new User(
                name, lastname, email, nickname, passwordEncoder.encode(password), "https://res.cloudinary.com/dr96a1nqv/image/upload/v1697035213/bk1136u5xdt1d6orehlq.jpg", null
        );
        userRepository.save(user);
        User u = userRepository.findByLogin(nickname);
        Role role = roleRepository.findByName("USER");
        userRepository.setRoles(u.getId(), role.getId());

        UserDto currentUser = get(nickname);

        if (isRemembered != null && isRemembered.equals(ServerResources.IS_REMEMBER_ME_PRESSED)) {
            remember(currentUser, resp);
        }
        return currentUser;
    }

    @Override
    public String updateInfo(String name, String lastname, String nickname, String email, String aboutMe, String emptyAboutMe, UserDto currentUserDto, HttpServletResponse resp) {
        if (name.trim().isEmpty()) {
            return "emptyName";
        }
        if (name.trim().length() > 60) {
            return "longName";
        }
        if (lastname.trim().isEmpty()) {
            return "emptyLastname";
        }
        if (lastname.trim().length() > 60) {
            return "longLastname";
        }
        if (nickname.trim().isEmpty()) {
            return "emptyNickname";
        }
        if (!nickname.equals(currentUserDto.getLogin())) {
            if (nickname.trim().length() < 5) {
                return "shortNickname";
            }
            if (nickname.trim().length() > 60) {
                return "longNickname";
            }
            if (!isNicknameUnique(nickname)) {
                return "nonUniqueNickname";
            }
        }
        if (email.isEmpty()) {
            return "emptyEmail";
        }
        if (!email.equals(currentUserDto.getEmail())) {
            if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                return "invalidEmail";
            }
            if (!isEmailUnique(email)) {
                return "nonUniqueEmail";
            }
        }
        if (aboutMe == null && emptyAboutMe != null) {
            aboutMe = emptyAboutMe;
        }

        User currentUser = userRepository.findByLogin(currentUserDto.getLogin());
        currentUser.setName(name);
        currentUser.setLastname(lastname);
        currentUser.setEmail(email);
        currentUser.setLogin(nickname);
        currentUser.setAboutMe(aboutMe);
        userRepository.save(currentUser);

        UserDto userDto = get(currentUser.getId());
        remember(userDto, resp);
        return "Your data was successfully changed!";
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
        userRepository.updateImageUrl(currentUser.getLogin(), imageUrl);
        UserDto userDto = get(currentUser.getLogin());
        httpSession.setAttribute(ServerResources.CURRENT_USER, userDto);

        file.deleteOnExit();
        resp.getWriter().write(imageUrl);
    }

    @Override
    public String updatePassword(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser) {
        if (!(currentPassword.isEmpty() && newPassword.isEmpty() && repeatPassword.isEmpty())) {
            if (currentPassword.isEmpty()) {
                return "emptyCurrentPassword";
            }
            if (!isPasswordMatches(currentUser.getLogin(), currentPassword)) {
                return "invalidPassword";
            }
            if (newPassword.isEmpty()) {
                return "emptyNewPassword";
            }
            if (newPassword.length() < 8) {
                return "shortPassword";
            }
            if (!newPassword.matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
                return "weakPassword";
            }
            if (!repeatPassword.equals(newPassword)) {
                return "invalidConfirmPassword";
            }

            userRepository.updatePassword(currentUser.getLogin(), passwordCryptographer.encrypt(newPassword));
            return "Your password was successfully changed!";
        }
        return "empty";
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
        User user = userRepository.findByLogin(nickname);
        return user.getPassword().equals(passwordCryptographer.encrypt(password));
    }

    @Override
    public void remember(UserDto user, HttpServletResponse resp) {
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
