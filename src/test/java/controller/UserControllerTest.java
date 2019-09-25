package controller;

import db.DataBase;
import http.common.ContentType;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    private UserController userController = UserController.getInstance();
    private HttpResponse httpResponse;
    private static final String USER_ID = "olaf";
    private static final String PASSWORD = "bmo";
    private static final String NAME = "bhy";
    private static final String EMAIL = "test@gmail.com";
    private static final String QUERY_STRING =
            "userId=" + USER_ID
                    + "&password=" + PASSWORD
                    + "&name=" + NAME
                    + "&email=" + EMAIL;

    private static final User USER = new User(USER_ID, PASSWORD, NAME, EMAIL);

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse();
    }

    @Test
    void 유저생성() {
        String url = "/user/create";

        RequestLine postRequestLine = new RequestLine("POST " + url + " HTTP/1.1");
        HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        RequestBody body = new RequestBody(QUERY_STRING, ContentType.FORM_URLENCODED);
        HttpRequest httpRequest = new HttpRequest(postRequestLine, httpHeader, body);

        userController.doPost(httpRequest, httpResponse);

        assertEquals(DataBase.findUserById(USER_ID), USER);
        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
    }
}