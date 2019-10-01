package webserver;

import controller.MainController;
import controller.StyleSheetController;
import controller.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UrlMapperTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private static final String GET_STYLESHEET_REQUEST_MESSAGE =
            "GET ./css/styles.css HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Accept: text/css,*/*;q=0.1\n" +
                    "Connection: keep-alive";

    private static final String FAIL_GET_REQUEST_MESSAGE =
            "GET /index2.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    @DisplayName("요청과 매핑되어있는 컨트롤러 호출")
    @Test
    void getController() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        assertThat(UrlMapper.getController(httpRequest)).isInstanceOf(MainController.class);
    }

    @DisplayName("CSS 컨트롤러 호출")
    @Test
    void getStyleSheetController() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_STYLESHEET_REQUEST_MESSAGE.getBytes()));
        assertThat(UrlMapper.getController(httpRequest)).isInstanceOf(StyleSheetController.class);
    }

    @DisplayName("요청과 매핑되어있는 컨트롤러가 없는 경우의 에러")
    @Test
    void FailToGetController() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(FAIL_GET_REQUEST_MESSAGE.getBytes()));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> UrlMapper.getController(httpRequest))
                .withMessage("404 Not Found");
    }
}
