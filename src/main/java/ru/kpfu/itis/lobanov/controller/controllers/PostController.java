package ru.kpfu.itis.lobanov.controller.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(ServerResources.POST_URL)
@SessionAttributes({ServerResources.CURRENT_USER, ServerResources.CURRENT_POST})
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MessageService messageService;

    @GetMapping
    public String getPostPage(
            @RequestParam(ServerResources.POST_NAME) String postName,
            @RequestParam(ServerResources.POST_AUTHOR) String postAuthor,
            Model model
    ) {
        PostDto postDto = postService.get(postName, postAuthor);

        List<MessageDto> messages = messageService.getAllFromPost(postName);
        messages.sort(Comparator.comparing(MessageDto::getDate));
        model.addAttribute(ServerResources.MESSAGES, messages);

        Object currentUser = model.getAttribute(ServerResources.CURRENT_USER);

        if (currentUser != null) {
            List<PostDto> posts = postService.getAllFavouriteFromUser(((UserDto) currentUser).getLogin());
            boolean isFavourite = posts.stream().anyMatch(post -> post.getName().equals(postName));

            if (isFavourite) {
                model.addAttribute(ServerResources.IS_FAVOURITE, true);
            }
        }

        model.addAttribute(ServerResources.CURRENT_POST, postDto);
        return ServerResources.POST_PAGE;
    }

    @PostMapping("/send-message")
    @ResponseBody
    public String sendMessage(
            @RequestParam(ServerResources.NEW_MESSAGE) String newMessage,
            @ModelAttribute(ServerResources.CURRENT_POST) PostDto postDto,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto
    ) {
        if (newMessage == null) {
            return "You entered wrong data";
        }
        MessageDto messageDto = messageService.save(newMessage, postDto, userDto);
        String json = new Gson().toJson(messageDto);
        json = json.replace("id", "messageID");
        json = json.replace("content", "messageContent");
        return json;
    }

    @PostMapping("press-like")
    @ResponseBody
    public String pressLike(
            @ModelAttribute(ServerResources.CURRENT_POST) PostDto postDto,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto,
            Model model
    ) {
        PostDto postDto1 = postService.updateLikes(postDto, userDto);
        model.addAttribute(ServerResources.CURRENT_POST, postDto1);
        return String.valueOf(postDto1.getLikes());
    }

    @PostMapping("press-message-like")
    @ResponseBody
    public String pressMessageLike(
            @RequestParam(ServerResources.MESSAGE_ID) String messageId,
            @ModelAttribute(ServerResources.CURRENT_POST) PostDto postDto,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto
    ) {
        int likes = messageService.updateLikes(Integer.parseInt(messageId), userDto);
        return String.valueOf(likes);
    }

    @PostMapping("press-favourite")
    public void pressFavourite(
            @ModelAttribute(ServerResources.CURRENT_POST) PostDto postDto,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto
    ) {
        postService.saveToFavourites(postDto, userDto);
    }

    @PostMapping("press-unfavourite")
    public void pressUnfavourite(
            @ModelAttribute(ServerResources.CURRENT_POST) PostDto postDto,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto
    ) {
        postService.removeFromFavourites(postDto, userDto);
    }
}
