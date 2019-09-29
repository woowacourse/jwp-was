package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestLineTest {
    @DisplayName("리퀘스트 라인 생성")
    @Test
    void create_common_notThrow() {
        assertDoesNotThrow(() -> {
            new RequestLine("GET", "/", "HTTP/1.1");
        });
    }

    @DisplayName("일반 리퀘스트 라인 값 얻기")
    @Test
    void get_common_equal() {
        RequestLine requestLine = new RequestLine("GET", "/?abc=123", "HTTP/1.1");
        assertThat(requestLine.getAbsPath()).isEqualTo("/");
        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getQueryString("abc")).isEqualTo("123");
        assertThat(requestLine.getUri()).isEqualTo(new RequestUri("/?abc=123"));
    }
}