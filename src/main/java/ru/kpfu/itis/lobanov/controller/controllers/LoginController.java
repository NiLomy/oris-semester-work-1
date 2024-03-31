//package ru.kpfu.itis.lobanov.controller.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kpfu.itis.lobanov.model.service.UserService;
//import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
//import ru.kpfu.itis.lobanov.util.dto.UserDto;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Controller
//@RequestMapping(ServerResources.LOGIN_URL)
//@SessionAttributes(ServerResources.CURRENT_USER)
//@RequiredArgsConstructor
//public class LoginController {
//    private final UserService userService;
//
//    @GetMapping
//    public String getLoginPage() {
//        return ServerResources.LOGIN_PAGE;
//    }
//
////    @PostMapping
////    @ResponseBody
////    public String signIn(
////            @RequestParam(ServerResources.NICKNAME) String login,
////            @RequestParam(ServerResources.PASSWORD) String password,
////            @RequestParam(ServerResources.REMEMBER_ME) String isRemembered,
////            Model model,
////            HttpServletResponse resp
////    ) {
////        UserDto userDto = userService.get(login, password);
////        if (userDto == null) {
////            return ServerResources.INVALID_INPUT;
////        } else {
//////            HttpSession httpSession = req.getSession();
//////            httpSession.setAttribute(ServerResources.CURRENT_USER, userDto);
////            model.addAttribute(ServerResources.CURRENT_USER, userDto);
////            if (isRemembered != null && isRemembered.equals(ServerResources.IS_REMEMBER_ME_PRESSED)) {
////                userService.remember(userDto, resp);
////            }
////            return ServerResources.VALID_INPUT;
////        }
////    }
//}
