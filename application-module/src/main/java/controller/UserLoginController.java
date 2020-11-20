package controller;

import domain.controller.AbstractController;
import domain.request.HttpRequest;
import domain.request.HttpSession;
import domain.response.HttpResponse;
import domain.response.ResponseCookie;
import service.UserService;
import utils.FilePrefixPathMapper;
import utils.StaticFileType;

public class UserLoginController extends AbstractController {

    private UserLoginController() {
        super();
    }

    public static UserLoginController getInstance() {
        return Cache.USER_LOGIN_CONTROLLER;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        UserService userService = UserService.getInstance();
        boolean isLogined = userService.isValidatedUser(userId, password);

        HttpSession session = httpRequest.getSession();
        session.setAttribute("logined", isLogined);
        String sessionId = session.getId();

        ResponseCookie responseCookie = new ResponseCookie("JSESSIONID", sessionId, "Path=/");
        httpResponse.addCookie(responseCookie);

        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);
        httpResponse.forward(filePath, StaticFileType.HTML);
    }

    private static class Cache {

        private static final UserLoginController USER_LOGIN_CONTROLLER = new UserLoginController();
    }
}
