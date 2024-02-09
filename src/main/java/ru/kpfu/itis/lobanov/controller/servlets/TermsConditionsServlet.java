package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = ServerResources.TERMS_AND_CONDITIONS_URL)
public class TermsConditionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ServerResources.TERMS_AND_CONDITIONS_PAGE).forward(req, resp);
    }
}
