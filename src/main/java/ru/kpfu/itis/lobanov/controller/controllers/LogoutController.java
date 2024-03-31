//package ru.kpfu.itis.lobanov.controller.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Controller
//@RequestMapping(ServerResources.LOGOUT_URL)
//public class LogoutController {
//    @GetMapping
//    public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        return clear(req, resp);
//    }
//
//    @PostMapping
//    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
//        return clear(req, resp);
//    }
//
//    private String clear(HttpServletRequest req, HttpServletResponse resp) {
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                c.setMaxAge(1);
//                resp.addCookie(c);
//            }
//        }
//
//        HttpSession session = req.getSession();
//        if (session != null) {
//            session.invalidate();
//        }
//
//        return "redirect:" + ServerResources.LOGIN_URL;
//    }
//}
