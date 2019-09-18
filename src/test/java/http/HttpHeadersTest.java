package http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    void httpHeaders_생성_확인() {
        List<String> lines = Arrays.asList("Host: localhost:8080", "Connection: keep-alive",
                "Content-Length: 59", "Content-Type: application/x-www-form-urlencoded", "Accept: */*");

        HttpHeaders httpHeaders = new HttpHeaders(lines);
        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.getHeader("Content-Length")).isEqualTo("59");
        assertThat(httpHeaders.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpHeaders.getHeader("Accept")).isEqualTo("*/*");
    }
}