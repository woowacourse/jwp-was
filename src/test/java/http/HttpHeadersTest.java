package http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static http.HttpHeaders.CONTENT_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    void header_속성에_해당하는_값을_가져온다() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "localhost:8080");
        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @Test
    void content_length_속성이_있는_경우() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        headers.put(CONTENT_LENGTH, "5");
        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isTrue();
    }

    @Test
    void content_length_속성이_없는_경우() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isFalse();
    }
}