package web.application.controller;

import web.application.common.FilePrefixPathMapper;
import web.application.service.UserService;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.Cookie;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

public class UserLoginController extends AbstractController {

    public static UserLoginController getInstance() {
        return Cache.USER_LOGIN_CONTROLLER;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        UserService userService = UserService.getInstance();
        boolean isLogined = userService.isValidatedUser(userId, password);

        Cookie cookie = new Cookie("logined", String.valueOf(isLogined), "Path=/");
        httpResponse.addCookie(cookie);

        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);
        httpResponse.forward(filePath, StaticFileType.HTML);
    }

    private static class Cache {

        private static final UserLoginController USER_LOGIN_CONTROLLER = new UserLoginController();
    }
}
