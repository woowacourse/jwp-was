package controller;

import domain.user.dto.UserListResponse;
import domain.user.service.UserService;
import utils.TemplateUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class UserListController extends AbstractController {
    private static final String USER_LIST = "user/list";
    private static final String USER_LOGIN_HTML = "/user/login.html";
    private static final String LOGIN_FALSE = "logined=false";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String cookieValue = httpRequest.getHeader(ResponseHeader.SET_COOKIE.get());
        if (cookieValue.contains(LOGIN_FALSE)) {
            httpResponse.forward(USER_LOGIN_HTML);
            return;
        }
        UserListResponse userListResponse = new UserListResponse(UserService.findAll());
        String profilePage = TemplateUtils.create(USER_LIST, userListResponse);
        httpResponse.forward(profilePage.getBytes());
    }
}
