package webserver;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileIoUtils;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestHeader;

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
        byte[] actual = requestHandler.handle(new HttpRequest(new HttpRequestHeader(HttpMethod.GET, resourcePath)));
        byte[] expected = FileIoUtils.loadFileFromClasspath(filePath);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("매칭되는 location이 없으면 Default 값을 반환한다.")
    @Test
    void defaultHandleTest() {
        byte[] actual = requestHandler.handle(new HttpRequest(new HttpRequestHeader(HttpMethod.GET, "/")));
        byte[] expected = RequestHandler.getDefaultBody();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원가입 테스트")
    @Test
    void joinHandleTest() {
        // given
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(HttpMethod.POST, "/user/create");
        Map<String, String> body = new HashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "박재성");
        body.put("email", "javajigi@slipp.net");

        // when
        byte[] actual = requestHandler.handle(new HttpRequest(httpRequestHeader, body));

        // then
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        byte[] expected = user.toString().getBytes();
        assertThat(actual).isEqualTo(expected);
    }
}