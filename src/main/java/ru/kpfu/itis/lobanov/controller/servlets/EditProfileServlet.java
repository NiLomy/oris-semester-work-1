package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = ServerResources.EDIT_PROFILE_URL)
@MultipartConfig(
        maxFileSize = ServerResources.MAX_FILE_SIZE,
        maxRequestSize = ServerResources.MAX_REQUEST_SIZE
)
public class EditProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute(ServerResources.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ServerResources.EDIT_PROFILE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ServerResources.ACTION_KEY);

        resp.setContentType(ServerResources.RESP_TYPE_PLAIN_TEXT);
        if (action == null) {
            userService.updateImageUrl(req, resp);
        } else {
            if (action.equals(ServerResources.UPDATE_INFO_ACTION)) userService.updateInfo(req, resp);
            if (action.equals(ServerResources.UPDATE_PASSWORD_ACTION)) userService.updatePassword(req, resp);
        }
    }
}
