package ru.kpfu.itis.lobanov.controller.listeners;

import ru.kpfu.itis.lobanov.model.dao.*;
import ru.kpfu.itis.lobanov.model.dao.impl.*;
import ru.kpfu.itis.lobanov.model.service.*;
import ru.kpfu.itis.lobanov.model.service.impl.*;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MessageDao messageDao = new MessageDaoImpl();
        MessageLikeDao messageLikeDao = new MessageLikeDaoImpl();
        PostDao postDao = new PostDaoImpl();
        PostLikeDao postLikeDao = new PostLikeDaoImpl();
        UserDao userDao = new UserDaoImpl();
        PostLikeService postLikeService = new PostLikeServiceImpl(postLikeDao);
        MessageLikeService messageLikeService = new MessageLikeServiceImpl(messageLikeDao);
        MessageService messageService = new MessageServiceImpl(messageDao, userDao, messageLikeService);
        PostService postService = new PostServiceImpl(postDao, userDao, postLikeService);
        UserService userService = new UserServiceImpl(userDao);
        sce.getServletContext().setAttribute(ServerResources.USER_SERVICE, userService);
        sce.getServletContext().setAttribute(ServerResources.POST_SERVICE, postService);
        sce.getServletContext().setAttribute(ServerResources.MESSAGE_SERVICE, messageService);
        sce.getServletContext().setAttribute(ServerResources.POST_LIKE_SERVICE, postLikeService);
        sce.getServletContext().setAttribute(ServerResources.MESSAGE_LIKE_SERVICE, messageLikeService);
    }
}
