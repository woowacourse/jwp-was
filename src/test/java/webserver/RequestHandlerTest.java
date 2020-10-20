package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileIoUtils;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    private RequestHandler requestHandler;

    @BeforeEach
    private void setUp() {
        requestHandler = new RequestHandler(new Socket());
    }

    @DisplayName("정적 파일 요청 핸들링 테스트")
    @ParameterizedTest
    @CsvSource({"/index.html,./templates/index.html", "/user/form.html,./templates/user/form.html"})
    void staticHandleTest(String resourcePath, String filePath) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new HttpRequestLine(HttpMethod.GET, resourcePath));
        HttpResponse httpResponse = new HttpResponse(null);

        requestHandler.handle(httpRequest, httpResponse);

        byte[] actual = FileIoUtils.loadFileFromClasspath(filePath);
        assertThat(httpResponse.getBody()).isEqualTo(actual);
    }

    @DisplayName("회원가입 시 새로운 페이지로 이동한다.")
    @Test
    void joinHandleTest() throws IOException, URISyntaxException {
        // given
        HttpRequestLine requestLine = new HttpRequestLine(HttpMethod.POST, "/user/create");
        Map<String, String> body = new HashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "박재성");
        body.put("email", "javajigi@slipp.net");

        // when
        HttpResponse response = new HttpResponse(null);
        requestHandler.handle(new HttpRequest(requestLine, body), response);

        // then
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo("http://localhost:8080/index.html");
    }
}