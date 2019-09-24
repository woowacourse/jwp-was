package http.request;

import http.common.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void 생성() {
        RequestLine requestLine = new RequestLine(
                new Url("/index.html"),
                HttpMethod.GET,
                HttpVersion.HTTP_1_1
        );

        assertThat(requestLine.getUrl()).isEqualTo(new Url("/index.html"));
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }
}
