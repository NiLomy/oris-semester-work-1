package ru.kpfu.itis.lobanov.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(ServerResources.FAVOURITE_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class FavouriteController {
    private final PostService postService;

    @GetMapping
    public String getFavouritePage(Model model, @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto) {
        List<PostDto> posts = postService.getAllFavouriteFromUser(userDto.getLogin());
        model.addAttribute(ServerResources.FAVOURITE_POSTS, posts);
        return ServerResources.FAVOURITE_PAGE;
    }
}
