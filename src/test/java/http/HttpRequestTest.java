package http;

import http.exception.EmptyHttpRequestException;
import http.exception.StartLineException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    @Test
    void Request가_비어있을_때_예외_발생() {
        BufferedReader request = new BufferedReader(new StringReader(""));

        assertThatThrownBy(() -> HttpRequest.of(request)).isInstanceOf(EmptyHttpRequestException.class);
    }

    @Test
    void Start_line의_parsing_결과가_3_개인지_확인() {
        BufferedReader request = new BufferedReader(
                new StringReader(
                        "GET /index.html\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*"
                )
        );

        assertThatThrownBy(() -> HttpRequest.of(request)).isInstanceOf(StartLineException.class);
    }

    @Test
    void Start_line_parsing_확인() throws IOException {
        BufferedReader request = new BufferedReader(
                new StringReader(
                        "GET /index.html HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*"
                )
        );

        HttpRequest httpRequest = HttpRequest.of(request);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getUrl()).isEqualTo("/index.html");
        assertThat(httpRequest.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }
}