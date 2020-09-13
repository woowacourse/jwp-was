package http;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {
    @DisplayName("from: 헤더 첫 라인을 입력받아 인스턴스 생성")
    @Test
    void from() throws IOException {
        // given
        String line = "GET / HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.from(line);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getUri().getPath()).isEqualTo("/");
        assertThat(requestLine.getUri().getQueryParameters()).isNull();
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}
