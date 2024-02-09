package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto get(int id);
    UserDto get(String nickname);
    UserDto get(String login, String password);
    List<UserDto> getAll();
    String getEmail(String login, String password);
    void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void updateInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void updateImageUrl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    boolean isEmailUnique(String email);
    boolean isNicknameUnique(String nickname);
    boolean isPasswordMatches(String nickname, String password);
    void remember(UserDto user, HttpServletRequest req, HttpServletResponse resp);
    void authUser(HttpServletRequest req, HttpServletResponse resp);
}
