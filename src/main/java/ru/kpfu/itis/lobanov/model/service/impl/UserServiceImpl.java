package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.PasswordUtil;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao<User> userDao = new UserDaoImpl();

    @Override
    public UserDto get(int id) {
        User user = userDao.get(id);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl());
    }

    @Override
    public UserDto get(String nickname) {
        User user = userDao.get(nickname);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getImageUrl());
    }

    @Override
    public UserDto get(String login, String password) {
        User user = userDao.get(login, password);
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastname(), user.getLogin(), user.getEmail(), user.getPassword(), user.getImageUrl());
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(
                u -> new UserDto(u.getName(), u.getLastname(), u.getLogin(), u.getEmail())
        ).collect(Collectors.toList());
    }

    @Override
    public String getEmail(String login, String password) {
        return userDao.getEmail(login, password);
    }

    @Override
    public void save(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public boolean update(User user, String oldLogin) {
        try {
            userDao.update(user, oldLogin);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    @Override
    public boolean updateImageUrl(String nickName, String imageUrl) {
        try {
            userDao.updateImageUrl(nickName, imageUrl);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(String nickname, String password) {
        try {
            userDao.updatePassword(nickname, PasswordUtil.encrypt(password));
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
    public boolean isPasswordMatches(String nickname, String password) {
        User user = userDao.get(nickname);
        return user.getPassword().equals(PasswordUtil.encrypt(password));
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
