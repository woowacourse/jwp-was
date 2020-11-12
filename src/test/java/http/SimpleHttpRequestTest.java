package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class SimpleHttpRequestTest {

    @Test
    void create() throws IOException {
        String request = "GET / HTTP/1.1" + "\n" +
        "Host: localhost:8080" + "\n" +
        "Connection: keep-alive" + "\n" +
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36" + "\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + "\n" +
        "Accept-Encoding: gzip, deflate, br" + "\n" +
        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + "\n" +
        "Cookie: _ga=GA1.1.1818307867.1604572661" + "\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);

        assertAll(
            () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(httpRequest.getURI()).isEqualTo("/")
        );
    }
}