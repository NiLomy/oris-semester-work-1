package ru.kpfu.itis.lobanov.controller.servlets;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.CloudinaryUtil;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "fileUpload", urlPatterns = "/upload")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class FileUploadingServlet extends HttpServlet {
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    public static final String FILE_PATH_PREFIX = "/tmp";
    public static final int DIRECTORIES_COUNT = 100;
    private UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/upload.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file");
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

//        File file = new File(FILE_PATH_PREFIX + File.separator
//                + filename.hashCode() % DIRECTORIES_COUNT + File.separator + filename);
        InputStream content = part.getInputStream();

        File file = File.createTempFile(filename, null);
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];
        Path p = file.toPath();

        content.read(buffer);

        outputStream.write(buffer);
        outputStream.close();

        Map map = cloudinary.uploader().upload(file, new HashMap<>());
        String imageUrl = (String) map.get("url");

        HttpSession httpSession = req.getSession();
        String nickname = (String) httpSession.getAttribute("userName");
        userService.updatePassword(nickname, imageUrl);
        UserDto userDto = userService.get(nickname);
        httpSession.setAttribute("currentUser", userDto);

        file.deleteOnExit();
        resp.setContentType("text/plain");
        resp.getWriter().write("Your image was successfully changed!");
    }
}
