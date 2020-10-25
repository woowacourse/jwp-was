package http.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.AbstractHttpRequestGenerator;
import http.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;

class UserListControllerTest extends AbstractHttpRequestGenerator {

    private Controller controller = new UserListController(HttpRequestMapping.GET("/user/list"));
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = HttpResponse.from(new ByteArrayOutputStream());
    }

    @DisplayName("로그인 후 요청시 Model 포함")
    @Test
    void hasModel_When_isLogined() throws IOException {
        httpRequest = createHttpGetRequest("GET_UserList_WithCookie");
        controller.handle(httpRequest, httpResponse);

        assertAll(
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(httpResponse.hasModel()).isTrue()
        );
    }

    @DisplayName("로그인 하지 않고 요청시 리다이렉트")
    @Test
    void redirect_When_isNotLogined() throws IOException {
        httpRequest = createHttpGetRequest("GET_UserList_Without_Cookie");
        controller.handle(httpRequest, httpResponse);

        assertAll(
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html"),
            () -> assertThat(httpResponse.hasModel()).isFalse()
        );
    }
}
