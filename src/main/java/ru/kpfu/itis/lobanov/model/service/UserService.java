package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto get(int id);

    UserDto get(String nickname);

    UserDto get(String login, String password);

    UserDto getByEmailAndPassword(String email, String password);

    List<UserDto> getAll();

    String getEmail(String login, String password);

    UserDto save(String name, String lastname, String email, String nickname, String password, String confirmPassword, String isRemembered, HttpServletResponse resp) throws ServletException, IOException;

    String updateInfo(String name, String lastname, String nickname, String email, String aboutMe, String emptyAboutMe, UserDto currentUserDto, HttpServletResponse resp);

    void updateImageUrl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    String updatePassword(String currentPassword, String newPassword, String repeatPassword, UserDto currentUser);

    boolean isEmailUnique(String email);

    boolean isNicknameUnique(String nickname);

    boolean isPasswordMatches(String nickname, String password);

    void remember(UserDto user, HttpServletResponse resp);

    void authUser(HttpServletRequest req, HttpServletResponse resp);
}
