package service;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.HttpRequestUrl;
import model.User;

public class UserServiceTest {

    @Test
    void create_get() {
        UserService userService = UserService.getInstance();

        HttpRequestLine httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"), "HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        HttpRequestBody httpRequestBody = HttpRequestBody.emptyBody();

        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
        userService.createRequestParams(httpRequest);

        final Collection<User> user = DataBase.findAll();

        assertThat(user).hasSize(1);
    }

    @Test
    void create_post() {
        UserService userService = UserService.getInstance();

        HttpRequestLine httpRequestLine = new HttpRequestLine("POST", new HttpRequestUrl("/user/create"), "HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
        userService.createRequestBody(httpRequest);

        final Collection<User> user = DataBase.findAll();

        assertThat(user).hasSize(1);
    }
}