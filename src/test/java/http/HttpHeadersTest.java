package http;

import org.junit.jupiter.api.Test;

import static http.HttpHeaders.CONTENT_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    void header_속성에_해당하는_값을_가져온다() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Host", "localhost:8080");

        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @Test
    void content_length_속성이_있는_경우() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(CONTENT_LENGTH, "5");

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isTrue();
    }

    @Test
    void content_length_속성이_없는_경우() {
        HttpHeaders httpHeaders = new HttpHeaders();

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isFalse();
    }
}