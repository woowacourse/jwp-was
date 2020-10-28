package controller;

import application.AbstractController;
import common.FilePrefixPathMapper;
import service.UserService;
import servlet.Cookie;
import servlet.HttpRequest;
import servlet.HttpResponse;
import servlet.HttpSession;
import servlet.StaticFileType;

public class UserLoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        UserService userService = UserService.getInstance();
        boolean isLogined = userService.isValidatedUser(userId, password);

        HttpSession session = httpRequest.getSession();
        session.setAttribute("logined", isLogined);
        String sessionId = session.getId();

        Cookie cookie = Cookie.of("JSESSIONID", sessionId);
        httpResponse.addCookie(cookie, "Path=/");

        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);
        httpResponse.forward(filePath, StaticFileType.HTML);
    }
}
