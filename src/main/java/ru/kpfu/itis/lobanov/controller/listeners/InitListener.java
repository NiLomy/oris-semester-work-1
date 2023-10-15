package ru.kpfu.itis.lobanov.controller.listeners;

import ru.kpfu.itis.lobanov.model.service.impl.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userService", new UserServiceImpl());
        sce.getServletContext().setAttribute("postService", new PostServiceImpl());
        sce.getServletContext().setAttribute("messageService", new MessageServiceImpl());
        sce.getServletContext().setAttribute("postLikeService", new PostLikeServiceImpl());
        sce.getServletContext().setAttribute("messageLikeService", new MessageLikeServiceImpl());
    }
}
