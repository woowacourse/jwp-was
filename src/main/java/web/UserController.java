package web;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class UserController extends AbstractController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.getPath().equals("/user/not")) {
            httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
            httpResponse.error();
            return;
        }
        if (httpRequest.getPath().equals("/user/list")) {
            User user = new User("1", "password", "name", "email@eamil.com");
            byte[] body = objectMapper.writeValueAsBytes(user);
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.addHeader("Content-Type", "application/json;charset=utf-8");
            httpResponse.addHeader("Content-Length", String.valueOf(body.length));
            httpResponse.forward(body);
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getParameter());
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
