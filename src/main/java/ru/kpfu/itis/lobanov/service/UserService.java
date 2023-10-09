package ru.kpfu.itis.lobanov.service;

import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    UserDto get(int id);
    UserDto get(String login, String password);
    List<UserDto> getAll();
    String getEmail(String login, String password);
    void save(User user);
    boolean update(User user, String oldLogin);
    boolean isEmailUnique(String email);
    void auth(UserDto user, HttpServletRequest req, HttpServletResponse resp);
    boolean isAuthorized(HttpServletRequest req, HttpServletResponse resp);
}
