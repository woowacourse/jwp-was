package http;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    // TODO: 2019-09-23 root directory 중복 제거
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    void header_속성에_해당하는_값을_가져온다() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "localhost:8080");
        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @Test
    void HttpHeader_생성() throws FileNotFoundException {
        List<String> lines = Arrays.asList("key1: value1", "key2: value2", "key3: value3");

        HttpHeaders httpHeaders = HttpHeaders.of(lines);

        assertThat(httpHeaders.getHeader("key1")).isEqualTo("value1");
        assertThat(httpHeaders.getHeader("key2")).isEqualTo("value2");
        assertThat(httpHeaders.getHeader("key3")).isEqualTo("value3");
    }

    @Test
    void content_length_속성이_있는_경우() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Length", "5");
        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.hasContentLength()).isTrue();
    }

    @Test
    void content_length_속성이_없는_경우() {
        HttpHeaders httpHeaders;
        Map<String, String> headers = new HashMap<>();

        httpHeaders = new HttpHeaders(headers);

        assertThat(httpHeaders.hasContentLength()).isFalse();
    }
}