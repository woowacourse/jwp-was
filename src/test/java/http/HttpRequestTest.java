package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HttpRequestTest {
    private static final RequestLine REQUEST_LINE = RequestLine.from("GET / HTTP/1.1");
    private static final HttpHeaders REQUEST_HTTP_HEADERS = HttpHeaders.from(
            Collections.singletonList("Host: localhost:8080"));
    private static final RequestBody REQUEST_BODY = RequestBody.from("userId=test@test.com&name=hello");

    private static Stream<Arguments> providePathAndResult() {
        return Stream.of(
                Arguments.of("/", true),
                Arguments.of("/users", false)
        );
    }

    @DisplayName("constructor: body가 없는 http 요청을 읽어 HttpRequest를 생성한다.")
    @Test
    void constructor_BodyIsNotEmpty() {
        // when
        HttpRequest httpRequest = new HttpRequest(REQUEST_LINE, REQUEST_HTTP_HEADERS, REQUEST_BODY);

        // then
        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isNotNull(),
                () -> assertThat(httpRequest.getHttpHeaders()).isNotNull(),
                () -> assertThat(httpRequest.getRequestBody()).isNotNull()
        );
    }

    @DisplayName("from: bufferedReader를 읽어 HttpRequest를 생성한다.")
    @Test
    void from() throws IOException {
        // given
        String input = "GET / HTTP/1.1" + System.lineSeparator()
                + "Host: localhost:8080" + System.lineSeparator()
                + "Content-Length: 31" + System.lineSeparator()
                + System.lineSeparator()
                + "userId=test@test.com&name=hello";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // when
        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        // then
        assertAll(
                () -> assertThat(httpRequest.equalsPath("/")).isTrue(),
                () -> assertThat(httpRequest.equalsMethod(HttpMethod.GET)).isTrue(),
                () -> assertThat(httpRequest.getRequestLine().getVersion()).isEqualTo("HTTP/1.1"),
                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("31"),
                () -> assertThat(httpRequest.getBodyValue("userId")).isEqualTo("test@test.com"),
                () -> assertThat(httpRequest.getBodyValue("name")).isEqualTo("hello")
        );
    }

    @DisplayName("equalsPath: 입력받은 path와 동일한 path를 가지는지 확인한다.")
    @MethodSource("providePathAndResult")
    @ParameterizedTest
    void equalsPath(final String path, final boolean expect) {
        // when
        HttpRequest httpRequest = new HttpRequest(REQUEST_LINE, REQUEST_HTTP_HEADERS, REQUEST_BODY);

        // when
        boolean actual = httpRequest.equalsPath(path);

        // then
        assertThat(actual).isEqualTo(expect);
    }
}
