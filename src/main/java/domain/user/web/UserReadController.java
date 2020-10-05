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

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = UserService.findById(httpRequest.getParameter(User.USER_ID));
        byte[] body = objectMapper.writeValueAsBytes(user);
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/json;charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
        httpResponse.forward(body);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        httpResponse.error();
    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
