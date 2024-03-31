package ru.kpfu.itis.lobanov.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO fix img
@Controller
@RequestMapping(ServerResources.EDIT_PROFILE_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
@MultipartConfig(
        maxFileSize = ServerResources.MAX_FILE_SIZE,
        maxRequestSize = ServerResources.MAX_REQUEST_SIZE
)
public class EditProfileController {
    private final UserService userService;

    @GetMapping
    public String getEditProfilePage() {
        return ServerResources.EDIT_PROFILE_PAGE;
    }

    @PostMapping("/photo")
    public void editProfile(
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser,
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        userService.updateImageUrl(req, resp);
    }

    @PostMapping("/info")
    @ResponseBody
    public String editInfo(
            @RequestParam(ServerResources.NAME) String name,
            @RequestParam(ServerResources.LASTNAME) String lastname,
            @RequestParam(ServerResources.EMAIL) String email,
            @RequestParam(ServerResources.NICKNAME) String nickname,
            @RequestParam(ServerResources.ABOUT_ME) String aboutMe,
            @RequestParam(ServerResources.EMPTY_ABOUT_ME) String emptyAboutMe,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser,
            Model model,
            HttpServletResponse resp
    ) {
        String message = userService.updateInfo(name, lastname, nickname, email, aboutMe, emptyAboutMe, currentUser, resp);
        UserDto userDto = userService.get(currentUser.getLogin());
        model.addAttribute(ServerResources.CURRENT_USER, userDto);
        return message;
    }

    @PostMapping("/password")
    @ResponseBody
    public String editPassword(
            @RequestParam(ServerResources.CURRENT_PASSWORD) String currentPassword,
            @RequestParam(ServerResources.NEW_PASSWORD) String newPassword,
            @RequestParam(ServerResources.REPEAT_PASSWORD) String repeatPassword,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser
    ) {
        return userService.updatePassword(currentPassword, newPassword, repeatPassword, currentUser);
    }
}
