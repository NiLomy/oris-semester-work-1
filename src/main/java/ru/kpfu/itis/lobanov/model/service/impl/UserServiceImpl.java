package ru.kpfu.itis.lobanov.model.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.lobanov.model.entity.Role;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.repositories.RoleRepository;
import ru.kpfu.itis.lobanov.model.repositories.UserRepository;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.security.CustomUserDetails;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Cloudinary cloudinary;
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
        User user = userRepository.findByLoginAndPassword(login, passwordEncoder.encode(password));
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getAboutMe());
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            return null;
        CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = u.getUser();
        return new UserDto(currentUser.getName(), currentUser.getLastname(), currentUser.getLogin(), currentUser.getEmail(), currentUser.getPassword(), currentUser.getImageUrl(), currentUser.getAboutMe());
    }

    @Override
    public UserDto getByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
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
    public String checkRegistrationForm(
            String name,
            String lastname,
            String email,
            String nickname,
            String password,
            String confirmPassword
    ) {
        if (name.trim().isEmpty()) {
            return ServerResources.EMPTY_NAME;
        }
        if (name.trim().length() > ServerResources.NAME_MAX_LENGTH) {
            return ServerResources.LONG_NAME;
        }
        if (lastname.trim().isEmpty()) {
            return ServerResources.EMPTY_LASTNAME;
        }
        if (lastname.trim().length() > ServerResources.LASTNAME_MAX_LENGTH) {
            return ServerResources.LONG_LASTNAME;
        }
        if (email.isEmpty()) {
            return ServerResources.EMPTY_EMAIL;
        }
        if (!email.matches(ServerResources.EMAIL_REGEX)) {
            return ServerResources.INVALID_EMAIl;
        }
        if (nickname.trim().isEmpty()) {
            return ServerResources.EMPTY_LOGIN;
        }
        if (nickname.trim().length() < ServerResources.LOGIN_MIN_LENGTH) {
            return ServerResources.SHORT_LOGIN;
        }
        if (nickname.trim().length() > ServerResources.LOGIN_MAX_LENGTH) {
            return ServerResources.LONG_LOGIN;
        }
        if (password.isEmpty()) {
            return ServerResources.EMPTY_PASSWORD;
        }
        if (password.length() < ServerResources.PASSWORD_MIN_LENGTH) {
            return ServerResources.SHORT_PASSWORD;
        }
        if (!password.matches(ServerResources.PASSWORD_REGEX)) {
            return ServerResources.WEAK_PASSWORD;
        }
        if (!confirmPassword.equals(password)) {
            return ServerResources.INVALID_CONFIRM_PASSWORD;
        }
        if (!isEmailUnique(email)) {
            return ServerResources.NON_UNIQUE_EMAIL;
        }
        if (!isNicknameUnique(nickname)) {
            return ServerResources.NON_UNIQUE_LOGIN;
        }

        return null;
    }

    @Override
    public UserDto save(
            String name,
            String lastname,
            String email,
            String login,
            String password,
            String confirmPassword
    ) {
        User user = new User(
                name,
                lastname,
                email,
                login,
                passwordEncoder.encode(password),
                ServerResources.DEFAULT_IMG_URL,
                null
        );
        userRepository.save(user);

        User u = userRepository.findByLogin(login);
        Role role = roleRepository.findByName(ServerResources.USER_AUTHORITY);
        userRepository.setRoles(u.getId(), role.getId());

        return get(login);
    }

    @Override
    public String checkUpdateInfoForm(String name, String lastname, String nickname, String email, String aboutMe, UserDto currentUserDto) {
        if (name.trim().isEmpty()) {
            return ServerResources.EMPTY_NAME;
        }
        if (name.trim().length() > ServerResources.NAME_MAX_LENGTH) {
            return ServerResources.LONG_NAME;
        }
        if (lastname.trim().isEmpty()) {
            return ServerResources.EMPTY_LASTNAME;
        }
        if (lastname.trim().length() > ServerResources.LASTNAME_MAX_LENGTH) {
            return ServerResources.LONG_LASTNAME;
        }
        if (nickname.trim().isEmpty()) {
            return ServerResources.EMPTY_LOGIN;
        }
        if (!nickname.equals(currentUserDto.getLogin())) {
            if (nickname.trim().length() < ServerResources.LOGIN_MIN_LENGTH) {
                return ServerResources.SHORT_LOGIN;
            }
            if (nickname.trim().length() > ServerResources.LOGIN_MAX_LENGTH) {
                return ServerResources.LONG_LOGIN;
            }
            if (!isNicknameUnique(nickname)) {
                return ServerResources.NON_UNIQUE_LOGIN;
            }
        }
        if (email.isEmpty()) {
            return ServerResources.EMPTY_EMAIL;
        }
        if (!email.equals(currentUserDto.getEmail())) {
            if (!email.matches(ServerResources.EMAIL_REGEX)) {
                return ServerResources.INVALID_EMAIl;
            }
            if (!isEmailUnique(email)) {
                return ServerResources.NON_UNIQUE_EMAIL;
            }
        }

        return null;
    }

    @Override
    public UserDto updateInfo(String name, String lastname, String nickname, String email, String aboutMe, UserDto currentUserDto) {
        User currentUser = userRepository.findByLogin(currentUserDto.getLogin());

        currentUser.setName(name);
        currentUser.setLastname(lastname);
        currentUser.setEmail(email);
        currentUser.setLogin(nickname);
        currentUser.setAboutMe(aboutMe);

        userRepository.save(currentUser);
        return get(currentUser.getId());
    }

    @Override
    public String updateImageUrl(MultipartFile image, UserDto currentUser) throws IOException {
        String filename = Paths.get(image.getName()).getFileName().toString();

        InputStream content = image.getInputStream();

        File file = File.createTempFile(filename, null);
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];

        content.read(buffer);

        outputStream.write(buffer);
        outputStream.close();

        Map map = cloudinary.uploader().upload(file, new HashMap<>());
        String imageUrl = (String) map.get(CLOUDINARY_URL_KEY);

        userRepository.updateImageUrl(currentUser.getLogin(), imageUrl);

        file.deleteOnExit();
        return imageUrl;
    }

    @Override
    public String checkUpdatePasswordForm(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser) {
        if (currentPassword.isEmpty()) {
            return ServerResources.EMPTY_CURRENT_PASSWORD;
        }
        if (!isPasswordMatches(currentUser.getLogin(), currentPassword)) {
            return ServerResources.INVALID_PASSWORD;
        }
        if (newPassword.isEmpty()) {
            return ServerResources.EMPTY_NEW_PASSWORD;
        }
        if (newPassword.length() < ServerResources.PASSWORD_MIN_LENGTH) {
            return ServerResources.SHORT_PASSWORD;
        }
        if (!newPassword.matches(ServerResources.PASSWORD_REGEX)) {
            return ServerResources.WEAK_PASSWORD;
        }
        if (!repeatPassword.equals(newPassword)) {
            return ServerResources.INVALID_CONFIRM_PASSWORD;
        }

        return null;
    }

    @Override
    public boolean updatePassword(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser) {
        if (!(currentPassword.isEmpty() && newPassword.isEmpty() && repeatPassword.isEmpty())) {
            userRepository.updatePassword(currentUser.getLogin(), passwordEncoder.encode(newPassword));
            return true;
        }
        return false;
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

        return passwordEncoder.matches(password, user.getPassword());
    }
}
