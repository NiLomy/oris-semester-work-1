package ru.kpfu.itis.lobanov.service.impl;

import ru.kpfu.itis.lobanov.model.dao.Dao;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.service.UserService;
import ru.kpfu.itis.lobanov.util.PasswordUtil;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final Dao<User> dao = new UserDaoImpl();

    @Override
    public UserDto get(int id) {
        User user = dao.get(id);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail());
    }

    @Override
    public UserDto get(String login, String password) {
        User user = dao.get(login, password);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword());
    }

    @Override
    public List<UserDto> getAll() {
        return dao.getAll().stream().map(
                u -> new UserDto(u.getName(), u.getLastname(), u.getLogin(), u.getEmail())
        ).collect(Collectors.toList());
    }

    @Override
    public String getEmail(String login, String password) {
        return dao.getEmail(login, password);
    }

    @Override
    public void save(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        dao.save(user);
    }

    @Override
    public boolean update(User user, String oldLogin) {
        try {
            dao.update(user, oldLogin);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    @Override
    public boolean isEmailUnique(String email) {
        return getAll().stream().noneMatch(userDto -> userDto.getEmail().equals(email));
    }

    @Override
    public void auth(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("user", user.getLogin());
        cookie.setMaxAge(24 * 3_600);
        resp.addCookie(cookie);
    }

    @Override
    public boolean isAuthorized(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    return true;
                }
            }
        }
        return false;
    }
}
