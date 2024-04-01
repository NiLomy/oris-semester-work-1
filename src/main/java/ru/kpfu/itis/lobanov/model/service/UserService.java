package ru.kpfu.itis.lobanov.model.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto get(int id);

    UserDto get(String nickname);

    UserDto get(String login, String password);

    UserDto getCurrentUser();

    UserDto getByEmailAndPassword(String email, String password);

    List<UserDto> getAll();

    String getEmail(String login, String password);

    String checkRegistrationForm(String name, String lastname, String email, String nickname, String password, String confirmPassword);

    UserDto save(String name, String lastname, String email, String nickname, String password, String confirmPassword);

    String checkUpdateInfoForm(String name, String lastname, String nickname, String email, String aboutMe, UserDto currentUserDto);

    UserDto updateInfo(String name, String lastname, String nickname, String email, String aboutMe, UserDto currentUserDto);

    String updateImageUrl(MultipartFile image, UserDto currentUser) throws IOException;

    String checkUpdatePasswordForm(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser);

    boolean updatePassword(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser);

    boolean isEmailUnique(String email);

    boolean isNicknameUnique(String nickname);

    boolean isPasswordMatches(String nickname, String password);
}
