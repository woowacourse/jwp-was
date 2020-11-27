package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class HttpHeadersTest {
    @Test
    void create() {
        String headerRequest =
            "Host: localhost:8080" + "\n" +
            "Connection: keep-alive" + "\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36" + "\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + "\n" +
            "Accept-Encoding: gzip, deflate, br" + "\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + "\n" +
            "Cookie: _ga=GA1.1.1818307867.1604572661" + "\n";

        HttpHeaders httpHeaders = HttpHeaders.from(headerRequest);

        assertAll(
            () -> assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(httpHeaders.getHeader("EMPTY")).isNull()
        );
    }
}