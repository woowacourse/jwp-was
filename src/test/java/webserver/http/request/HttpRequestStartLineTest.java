package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpRequestStartLineTest {
    @DisplayName("생성자 테스트")
    @Test
    void ofTest() {
        String request = "GET /index.html HTTP/1.1";
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.of(request);

        assertAll(
                () -> assertThat(httpRequestStartLine.getHttpMethodType()).isEqualTo(HttpMethodType.GET),
                () -> assertThat(httpRequestStartLine.getUrl()).isEqualTo("/index.html"),
                () -> assertThat(httpRequestStartLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1)
        );
    }
}
