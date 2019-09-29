package http.application.controller;

import http.application.Controller;
import http.common.HttpSession;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private Controller controller;
    private InputStream in;

    @BeforeEach
    void setUp() {
        controller = new CreateUserController();
    }

    @Test
    @DisplayName("유저 생성시 정상적으로 index.html로 리다이렉팅하는지 테스트")
    void 유저_생성_정상_흐름() throws IOException {
        in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/http_post.txt");

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse(new HttpSession(UUID.randomUUID()));
        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Location")).isEqualTo("/index.html");
    }
}