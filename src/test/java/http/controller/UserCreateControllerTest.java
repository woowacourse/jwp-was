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

class UserCreateControllerTest extends AbstractHttpRequestGenerator {

    private Controller controller = new UserCreateController(HttpRequestMapping.POST("/user/create"));
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() throws IOException {
        httpRequest = createHttpPostRequest("POST_UserCreateRequest");
        httpResponse = HttpResponse.from(new ByteArrayOutputStream());
    }

    @DisplayName("회원가입 후 메인화면으로 리다이렉트")
    @Test
    void redirect_After_User_Registration() {
        assertThat(controller.canHandle(httpRequest)).isTrue();

        controller.handle(httpRequest, httpResponse);

        assertAll(
            () -> assertThat(httpResponse.hasResource()).isFalse(),
            () -> assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html"),
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND)
        );
    }
}
