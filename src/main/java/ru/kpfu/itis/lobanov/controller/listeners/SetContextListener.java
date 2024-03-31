//package ru.kpfu.itis.lobanov.controller.listeners;
//
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
//
//import javax.servlet.ServletRequestEvent;
//import javax.servlet.ServletRequestListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//public class SetContextListener implements ServletRequestListener {
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        sre.getServletRequest().setAttribute(ServerResources.PAGE_CONTEXT, sre.getServletContext().getContextPath());
//    }
//}
