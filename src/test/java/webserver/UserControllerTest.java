package webserver;

import controller.UserController;
import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    RequestLine postRequestLine = new RequestLine("POST / HTTP/1.1");
    HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
    RequestBody body = new RequestBody("", "");
    private static final String USER_ID = "olaf";
    private static final String PASSWORD = "bmo";
    private static final String NAME = "bhy";
    private static final String EMAIL = "test@gmail.com";
    private static final String QUERY_STRING =
            "userId=" + USER_ID
                    + "&password=" + PASSWORD
                    + "&name=" + NAME
                    + "&email=" + EMAIL;

    @Test
    void create() {
        UserController userController = UserController.getInstance();
        HttpRequest httpRequest = new HttpRequest(postRequestLine, httpHeader, body);
        HttpResponse httpResponse = new HttpResponse();

        User user = new User(USER_ID, PASSWORD, NAME, EMAIL);
        userController.doPost(httpRequest, httpResponse);
        assertEquals(DataBase.findUserById(USER_ID), user);
        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
    }
}