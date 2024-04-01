package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

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
    @ResponseBody
    public String register(
            @RequestParam(ServerResources.NAME) String name,
            @RequestParam(ServerResources.LASTNAME) String lastname,
            @RequestParam(ServerResources.EMAIL) String email,
            @RequestParam(ServerResources.NICKNAME) String nickname,
            @RequestParam(ServerResources.PASSWORD) String password,
            @RequestParam(ServerResources.CONFIRM_PASSWORD) String confirmPassword,
            Model model
    ) {
        String response = userService.checkRegistrationForm(name, lastname, email, nickname, password, confirmPassword);
        if (response != null) return response;

        UserDto currentUser = userService.save(name, lastname, email, nickname, password, confirmPassword);
        model.addAttribute(ServerResources.CURRENT_USER, currentUser);
        return ServerResources.HOME_URL;
    }
}
