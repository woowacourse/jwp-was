package service;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.HttpRequestUrl;
import model.User;

public class UserServiceTest {

    private final UserService userService = UserService.getInstance();

    @BeforeEach
    void setUp() {
        DataBase.reset();
    }

    @Test
    void create_get() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"), "HTTP/1.1");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        final HttpRequestBody httpRequestBody = HttpRequestBody.emptyBody();
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);

        // when
        userService.createRequestParams(httpRequest);

        // then
        final Collection<User> user = DataBase.findAll();
        assertThat(user).hasSize(1);
    }

    @Test
    void create_post() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("POST", new HttpRequestUrl("/user/create"), "HTTP/1.1");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        final HttpRequestBody httpRequestBody = new HttpRequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);

        // when
        userService.createRequestBody(httpRequest);

        // then
        final Collection<User> user = DataBase.findAll();
        assertThat(user).hasSize(1);
    }

    @Test
    void login() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("POST", new HttpRequestUrl("/user/create"), "HTTP/1.1");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        final HttpRequestBody httpRequestBody = new HttpRequestBody("userId=tigger&password=password&name=티거&email=tigger@tigger.com");
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
        userService.createRequestBody(httpRequest);

        // when
        final User loginUser = userService.login(httpRequest.getHttpRequestBodyByName("userId"));

        // then
        assertThat(loginUser.getUserId()).isEqualTo("tigger");
        assertThat(loginUser.getPassword()).isEqualTo("password");
        assertThat(loginUser.getName()).isEqualTo("티거");
        assertThat(loginUser.getEmail()).isEqualTo("tigger@tigger.com");
    }
}