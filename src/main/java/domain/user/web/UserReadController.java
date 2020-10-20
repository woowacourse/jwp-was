package domain.user.web;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class UserReadController extends AbstractController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final UserService userService;

    public UserReadController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = userService.findByUserId(httpRequest.getParameter(User.USER_ID));
        if (user == null) {
            validateParameter(httpRequest, httpResponse);
            httpResponse.error();
        }
        if (user != null) {
            byte[] body = objectMapper.writeValueAsBytes(user);
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/json;charset=utf-8");
            httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
            httpResponse.forward(body);
        }
    }

    private void validateParameter(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.containsParameter(User.USER_ID)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        if (httpRequest.containsParameter(User.USER_ID)) {
            httpResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
