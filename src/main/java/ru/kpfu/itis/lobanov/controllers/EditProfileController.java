package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import java.io.IOException;

@Controller
@RequestMapping(ServerResources.EDIT_PROFILE_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class EditProfileController {
    private final UserService userService;

    @GetMapping
    public String getEditProfilePage() {
        return ServerResources.EDIT_PROFILE_PAGE;
    }

    @PostMapping(ServerResources.PHOTO_URL)
    @ResponseBody
    public String editProfile(
            @RequestParam(ServerResources.FILE) MultipartFile image,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser,
            Model model
    ) throws IOException {
        String imgUrl = userService.updateImageUrl(image, currentUser);
        UserDto userDto = userService.get(currentUser.getLogin());
        model.addAttribute(ServerResources.CURRENT_USER, userDto);
        return imgUrl;
    }

    @PostMapping(ServerResources.INFO_URL)
    @ResponseBody
    public String editInfo(
            @RequestParam(ServerResources.NICKNAME) String nickname,
            @RequestParam(ServerResources.NAME) String name,
            @RequestParam(ServerResources.LASTNAME) String lastname,
            @RequestParam(ServerResources.EMAIL) String email,
            @RequestParam(ServerResources.ABOUT_ME) String aboutMe,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser,
            Model model
    ) {
        String response = userService.checkUpdateInfoForm(name, lastname, nickname, email, aboutMe, currentUser);
        if (response != null) return response;

        UserDto userDto = userService.updateInfo(name, lastname, nickname, email, aboutMe, currentUser);
        model.addAttribute(ServerResources.CURRENT_USER, userDto);
        return ServerResources.SUCCESS_RESPONSE;
    }

    @PostMapping(ServerResources.PASSWORD_URL)
    @ResponseBody
    public String editPassword(
            @RequestParam(ServerResources.CURRENT_PASSWORD) String currentPassword,
            @RequestParam(ServerResources.NEW_PASSWORD) String newPassword,
            @RequestParam(ServerResources.REPEAT_PASSWORD) String repeatPassword,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto currentUser
    ) {
        String response = userService.checkUpdatePasswordForm(currentPassword, newPassword, repeatPassword, currentUser);
        if (response != null) return response;

        userService.updatePassword(currentPassword, newPassword, repeatPassword, currentUser);
        return ServerResources.SUCCESS_RESPONSE;
    }
}
