package web;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeaders;
import webserver.http.request.RequestMethod;
import webserver.http.request.RequestLine;
import webserver.http.request.RequestUrl;

class UserControllerTest {
    private UserController userController = new UserController();

    @DisplayName("유저를 생성한다.")
    @Test
    void createUser() {
        RequestLine requestLine = new RequestLine(RequestMethod.POST, RequestUrl.from("/user/create"),
            "HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, new RequestBody("userId=hello&password=password&name=myname&email=is@name.com"));

        assertThat(userController.createUser(httpRequest))
            .extracting("responseStatus")
            .extracting("httpStatus")
            .extracting("statusCode").isEqualTo(302);

        assertThat(DataBase.findUserById("hello")).extracting("name").isEqualTo("myname");
    }

    @DisplayName("잘못된 값이 들어올 시 400 처리")
    @Test
    void createUserWithException() {
        RequestLine requestLine = new RequestLine(RequestMethod.POST, RequestUrl.from("/user/create"),
            "HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, new RequestBody("name.com"));

        assertThat(userController.createUser(httpRequest))
            .extracting("responseStatus")
            .extracting("httpStatus")
            .extracting("statusCode").isEqualTo(400);
    }
}