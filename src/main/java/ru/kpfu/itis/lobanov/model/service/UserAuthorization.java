package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthorization {
    public void auth(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("user", user.getLogin());
        cookie.setMaxAge(24 * 3_600);
        resp.addCookie(cookie);
    }

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
//        return req.getSession().getAttribute("user") != null;
    }
}
