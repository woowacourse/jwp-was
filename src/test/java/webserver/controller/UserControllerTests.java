package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.RequestMapper;
import webserver.controller.response.HttpResponse;

import java.io.IOException;

import static exception.InvalidSignUpDataException.INVALID_SIGN_UP_DATA_MESSAGE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static webserver.controller.UserController.SAVE_REDIRECT_URL;

public class UserControllerTests {
    private static final String REQUEST_PARAM = "userId=kangmin&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=kangmin789%40slipp.net";
    private static final String INVALID_REQUEST_PARAM = "userId=kangmin&password=password";
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    RequestMapper requestMapper;

    @BeforeEach
    void setUp() {
        httpRequest = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);
        requestMapper = new RequestMapper();

    }

    @Test
    @DisplayName("정상 save 요청")
    void save() throws IOException {
        when(httpRequest.readData()).thenReturn(REQUEST_PARAM);
        when(httpRequest.getPath()).thenReturn("/user/create");
        requestMapper.executeMapping(httpRequest, httpResponse);
        User user = DataBase.findUserById("kangmin");
        assertThat(user.getEmail()).isEqualTo("kangmin789%40slipp.net");
        assertThat(user.getPassword()).isEqualTo("password");
        verify(httpResponse).redirect302Found(SAVE_REDIRECT_URL);
    }

    @Test
    void invalid_RequestParam() throws IOException {
        when(httpRequest.readData()).thenReturn(INVALID_REQUEST_PARAM);
        when(httpRequest.getPath()).thenReturn("/user/create");
        requestMapper.executeMapping(httpRequest, httpResponse);

        verify(httpResponse).badRequest(INVALID_SIGN_UP_DATA_MESSAGE);
    }
}
