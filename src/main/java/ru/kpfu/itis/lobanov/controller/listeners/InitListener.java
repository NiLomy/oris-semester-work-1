package ru.kpfu.itis.lobanov.controller.listeners;

import ru.kpfu.itis.lobanov.service.impl.UserServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userService", new UserServiceImpl());
    }
}
