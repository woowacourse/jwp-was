package controller;

import http.response.HttpResponse;
import http.response.ResponseStatus;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.net.HttpHeaders.LOCATION;
import static controller.LoginController.LOGIN_FAILED_REDIRECT_LOCATION;
import static controller.LoginController.LOGIN_SUCCESS_REDIRECT_LOCATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest extends AbstractControllerTest {
    private User signUpUser = new User("signUpId", "password", "name", "email");

    @BeforeEach
    void setUp() {
        signUp(signUpUser);
    }

    @Test
    void DoPost_로그인_성공() {
        HttpResponse httpResponse = login(signUpUser.getUserId(), signUpUser.getPassword());
        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
        assertEquals(httpResponse.getHttpHeader().getHeaderAttribute(LOCATION), LOGIN_SUCCESS_REDIRECT_LOCATION);
    }

    @Test
    void DoPost_로그인_실패() {
        HttpResponse httpResponse = login("none", "none");
        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
        assertEquals(httpResponse.getHttpHeader().getHeaderAttribute(LOCATION), LOGIN_FAILED_REDIRECT_LOCATION);
    }
}