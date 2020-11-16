package domain.user.web;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import session.service.SessionService;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class UserListController extends AbstractController {
    private final UserService userService;
    private final SessionService sessionService;
    private final ObjectMapper objectMapper;

    public UserListController(UserService userService, SessionService sessionService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.notAuthorized()) {
            httpResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        List<User> list = userService.list();
        byte[] bytes = objectMapper.writeValueAsBytes(list);
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/json;charset=utf-8");
        httpResponse.forward(bytes);
    }
}
