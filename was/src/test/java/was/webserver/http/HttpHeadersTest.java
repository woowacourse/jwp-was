package was.webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpHeadersTest {
    @DisplayName("요청시 헤더를 포함하지 않았을 때 헤더 생성 테스트")
    @Test
    void ofWithEmptyHeadersTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("\nthis is body");

        assertThat(httpHeaders.getHttpHeaders()).hasSize(0);
    }

    @DisplayName("요청시 헤더가 포함되어 있을 때 헤더 생성 테스트")
    @Test
    void ofWithNonEmptyHeadersTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\n\nthis is body");

        assertThat(httpHeaders.getHttpHeaders()).hasSize(1);
    }

    @DisplayName("입력한 HttpHeader가 포함되어 있는지 확인한다")
    @Test
    void containsTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\nContent-Length: 59");

        assertThat(httpHeaders.contains(HttpHeader.of(HttpHeaderType.CONTENT_LENGTH))).isTrue();
    }

    @DisplayName("HttpHeaders에서 입력한 HttpHeader의 값 추출")
    @Test
    void getHttpHeaderTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\nContent-Length: 59");
        HttpHeader httpHeader = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH);

        String expected = httpHeaders.getHttpHeader(httpHeader);

        assertThat(expected).isEqualTo("59");
    }

    @DisplayName("포함되지 않은 HttpHeader 입력 시 예외 반환")
    @Test
    void getHttpHeaderWithInvalidHeaderTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\nContent-Length: 59");
        HttpHeader httpHeader = HttpHeader.of(HttpHeaderType.LOCATION);

        assertThatThrownBy(() -> {
            httpHeaders.getHttpHeader(httpHeader);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("httpHeader 추가")
    @Test
    void addTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\nContent-Length: 59");
        httpHeaders.add("Set-Cookie", "logined=false");
        List<HttpHeader> headers = httpHeaders.getHttpHeaders();

        assertAll(
                () -> assertThat(headers).hasSize(3),
                () -> assertThat(headers).contains(HttpHeader.of("Set-Cookie", "logined=false"))
        );
    }

    @DisplayName("헤더 출력 정보 확인")
    @Test
    void getHttpHeadersStringTest() throws IOException {
        HttpHeaders httpHeaders = createHttpHeaders("Host: localhost:8080\nContent-Length: 59");
        String httpHeadersString = httpHeaders.getHttpHeadersString();

        assertThat(httpHeadersString).isEqualTo("Host: localhost:8080" + System.lineSeparator()
                + "Content-Length: 59" + System.lineSeparator());
    }

    private HttpHeaders createHttpHeaders(String headers) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(headers.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return HttpHeaders.of(bufferedReader);
    }
}
