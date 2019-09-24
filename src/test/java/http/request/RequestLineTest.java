package http.request;

import http.common.HttpMethod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    private static Stream<Arguments> provideRequestLine() {
        return Stream.of(
                Arguments.of("GET /user/create?userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1",
                        HttpMethod.GET,
                        "/user/create",
                        "userId=aiden&password=password&name=aiden&email=aiden@aiden.com",
                        "HTTP/1.1",
                        "http"),
                Arguments.of("GET /index.html HTTP/1.1",
                        HttpMethod.GET,
                        "/index.html",
                        "",
                        "HTTP/1.1",
                        "http"),
                Arguments.of("POST /user/create HTTP/1.1",
                        HttpMethod.POST,
                        "/user/create",
                        "",
                        "HTTP/1.1",
                        "http")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRequestLine")
    public void requestLineTest(String data, HttpMethod method, String path,
                                String queryString, String httpVersion, String protocol) throws IOException {
        RequestLine requestLine = RequestLine.of(data);

        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getQueryString()).isEqualTo(queryString);
        assertThat(requestLine.getHttpVersion()).isEqualTo(httpVersion);
        assertThat(requestLine.getProtocol()).isEqualTo(protocol);
    }
}