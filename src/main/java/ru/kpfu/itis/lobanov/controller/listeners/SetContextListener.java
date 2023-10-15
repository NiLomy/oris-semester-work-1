package ru.kpfu.itis.lobanov.controller.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SetContextListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletRequest().setAttribute("pageContext", sre.getServletContext().getContextPath());
    }
}
