package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    UserDto get(int id);
    UserDto get(String nickname);
    UserDto get(String login, String password);
    List<UserDto> getAll();
    String getEmail(String login, String password);
    void save(User user);
    boolean update(User user, String oldLogin);
    boolean updateImageUrl(String nickName, String imageUrl);
    boolean updatePassword(String nickname, String password);
    boolean isEmailUnique(String email);
    boolean isNicknameUnique(String nickname);
    boolean isPasswordMatches(String nickname, String password);
    void remember(UserDto user, HttpServletRequest req, HttpServletResponse resp);
}
