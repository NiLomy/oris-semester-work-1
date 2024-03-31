package ru.kpfu.itis.lobanov.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(ServerResources.REGISTRATION_URL)
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String getRegistrationPage() {
        return ServerResources.REGISTRATION_PAGE;
    }

    @PostMapping
    public String register(
            @RequestParam(ServerResources.NAME) String name,
            @RequestParam(ServerResources.LASTNAME) String lastname,
            @RequestParam(ServerResources.EMAIL) String email,
            @RequestParam(ServerResources.NICKNAME) String nickname,
            @RequestParam(ServerResources.PASSWORD) String password,
            @RequestParam(ServerResources.CONFIRM_PASSWORD) String confirmPassword,
            @RequestParam(ServerResources.REMEMBER_ME) String rememberMe,
            Model model,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        UserDto currentUser = userService.save(name, lastname, email, nickname, password, confirmPassword, rememberMe, resp);
        model.addAttribute(ServerResources.CURRENT_USER, currentUser);
        return "redirect:" + ServerResources.HOME_URL;
    }
}
