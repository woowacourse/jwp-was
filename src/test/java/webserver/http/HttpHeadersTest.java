package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpHeadersTest {
    @DisplayName("요청시 헤더를 포함하지 않았을 때 헤더 생성 테스트")
    @Test
    void ofWithEmptyHeadersTest() throws IOException {
        InputStream emptyInputStream = new ByteArrayInputStream("\nthis is body".getBytes());
        InputStreamReader emptyInputStreamReader = new InputStreamReader(emptyInputStream);
        BufferedReader emptyBufferedReader = new BufferedReader(emptyInputStreamReader);
        HttpHeaders httpHeaders = HttpHeaders.of(emptyBufferedReader);

        assertAll(
                () -> assertThat(httpHeaders.getHttpHeadersState()).isEqualTo(HttpHeadersState.EMPTY),
                () -> assertThat(httpHeaders.getHttpHeaders()).hasSize(0)
        );
    }

    @DisplayName("요청시 헤더가 포함되어 있을 때 헤더 생성 테스트")
    @Test
    void ofWithNonEmptyHeadersTest() throws IOException {
        InputStream nonEmptyInputStream = new ByteArrayInputStream("Host: localhost:8080\n\nthis is body".getBytes());
        InputStreamReader nonEmptyInputStreamReader = new InputStreamReader(nonEmptyInputStream);
        BufferedReader nonEmptyBufferedReader = new BufferedReader(nonEmptyInputStreamReader);
        HttpHeaders httpHeaders = HttpHeaders.of(nonEmptyBufferedReader);

        assertAll(
                () -> assertThat(httpHeaders.getHttpHeadersState()).isEqualTo(HttpHeadersState.NOT_EMPTY),
                () -> assertThat(httpHeaders.getHttpHeaders()).hasSize(1)
        );
    }
}
