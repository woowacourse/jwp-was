package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("입력한 HttpHeader가 포함되어 있는지 확인한다")
    @Test
    void containsTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("Host: localhost:8080\nContent-Length: 59".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        HttpHeaders httpHeaders = HttpHeaders.of(bufferedReader);

        assertThat(httpHeaders.contains(HttpHeader.of(HttpHeaderType.CONTENT_LENGTH))).isTrue();
    }

    @DisplayName("HttpHeaders에서 입력한 HttpHeader의 값 추출")
    @Test
    void getHttpHeaderTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("Host: localhost:8080\nContent-Length: 59".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        HttpHeaders httpHeaders = HttpHeaders.of(bufferedReader);
        HttpHeader httpHeader = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH);

        String expected = httpHeaders.getHttpHeader(httpHeader);

        assertThat(expected).isEqualTo("59");
    }

    @DisplayName("포함되지 않은 HttpHeader 입력 시 예외 반환")
    @Test
    void getHttpHeaderWithInvalidHeaderTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("Host: localhost:8080\nContent-Length: 59".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        HttpHeaders httpHeaders = HttpHeaders.of(bufferedReader);
        HttpHeader httpHeader = HttpHeader.of(HttpHeaderType.LOCATION);

        assertThatThrownBy(() -> {
            httpHeaders.getHttpHeader(httpHeader);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
