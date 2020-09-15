package http;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {
    private static final RequestLine REQUEST_LINE = RequestLine.from("GET / HTTP/1.1");
    private static final RequestHeaders REQUEST_HEADERS = RequestHeaders.from(
            Arrays.asList("Host: localhost:8080", "Sec-Fetch-Site: none", "Sec-Fetch-Mode: navigate",
                    "Sec-Fetch-User: ?1"));
    private static final RequestBody REQUEST_BODY = RequestBody.from(
            "userId=test@test.com&password=1q2w3e&name=hello&email=nullable@kakao.com");

    @DisplayName("constructor: body가 없는 http 요청을 읽어 HttpRequest 생성")
    @Test
    void constructor_BodyIsEmpty() {
        // when
        HttpRequest httpRequest = new HttpRequest(REQUEST_LINE, REQUEST_HEADERS);

        // then
        assertThat(httpRequest.getRequestLine()).isNotNull();
        assertThat(httpRequest.getRequestHeaders()).isNotNull();
        assertThat(httpRequest.getRequestBody()).isNull();
    }

    @DisplayName("constructor: body가 없는 http 요청을 읽어 HttpRequest 생성")
    @Test
    void constructor_BodyIsNotEmpty() {
        // when
        HttpRequest httpRequest = new HttpRequest(REQUEST_LINE, REQUEST_HEADERS, REQUEST_BODY);

        // then
        assertThat(httpRequest.getRequestLine()).isNotNull();
        assertThat(httpRequest.getRequestHeaders()).isNotNull();
        assertThat(httpRequest.getRequestBody()).isNotNull();
    }
}
