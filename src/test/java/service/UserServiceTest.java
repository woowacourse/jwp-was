package service;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import db.DataBase;
import http.request.Request;
import http.request.RequestBody;
import http.request.RequestHeader;
import http.request.RequestLine;
import model.User;

public class UserServiceTest {

    @Test
    void create() {
        UserService userService = UserService.getInstance();

        RequestLine requestLine = new RequestLine("POST", "/user/create", "HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader();
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        Request request = new Request(requestLine, requestHeader, requestBody);
        userService.create(request);

        final Collection<User> user = DataBase.findAll();

        assertThat(user).hasSize(1);
    }
}