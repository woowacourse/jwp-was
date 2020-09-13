package http;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {
    @DisplayName("from: body가 없는 http 요청을 읽어 HttpRequest 생성")
    @Test
    void constructor() {
        // given
        final List<String> requestHeaders = Arrays.asList(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Sec-Fetch-Site: none",
                "Sec-Fetch-Mode: navigate",
                "Sec-Fetch-User: ?1",
                "Sec-Fetch-Dest: document");

        // when
        HttpRequest httpRequest = HttpRequest.from(requestHeaders);

        // then
        assertThat(httpRequest.getRequestLine()).isNotNull();
        assertThat(httpRequest.getRequestHeaders()).isNotNull();
        assertThat(httpRequest.getRequestBody()).isNull();
    }
}
