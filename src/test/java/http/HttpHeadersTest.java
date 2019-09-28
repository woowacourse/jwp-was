package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.HttpHeaders.CONTENT_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    @DisplayName("header 속성에 해당하는 값을 가져온다")
    void getHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Host", "localhost:8080");

        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @Test
    @DisplayName("header가 있는 경우")
    void existHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(CONTENT_LENGTH, "5");

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isTrue();
    }

    @Test
    @DisplayName("header가 없는 경우")
    void notExistHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        assertThat(httpHeaders.existHeader(CONTENT_LENGTH)).isFalse();
    }
}