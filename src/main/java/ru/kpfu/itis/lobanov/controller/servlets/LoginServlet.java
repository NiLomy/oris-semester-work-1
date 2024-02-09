package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = ServerResources.LOGIN_URL)
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute(ServerResources.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ServerResources.LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ServerResources.NICKNAME);
        String password = req.getParameter(ServerResources.PASSWORD);
        String isRemembered = req.getParameter(ServerResources.REMEMBER_ME);

        resp.setContentType(ServerResources.RESP_TYPE_PLAIN_TEXT);
        UserDto userDto = userService.get(login, password);
        if (userDto == null) {
            resp.getWriter().write(ServerResources.INVALID_INPUT);
        } else {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute(ServerResources.CURRENT_USER, userDto);
            if (isRemembered != null && isRemembered.equals(ServerResources.IS_REMEMBER_ME_PRESSED)) {
                userService.remember(userDto, req, resp);
            }
            resp.getWriter().write(ServerResources.VALID_INPUT);
        }
    }
}
