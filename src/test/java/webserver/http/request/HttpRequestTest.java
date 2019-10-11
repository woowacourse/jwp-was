package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpRequestTest {
    HttpRequest httpRequest;
    String sessionId;
    @BeforeEach
    void setup() throws IOException {
        httpRequest = new HttpRequest(
                new RequestLine("GET", "/", "HTTP/1.1"),
                new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null));
        sessionId = httpRequest.getSession().getId();
    }

    @DisplayName("리퀘스트 객체 생성")
    @Test
    void createHttpRequest_commonRequest_notThrow() {
        assertDoesNotThrow(() -> new HttpRequest(
                new RequestLine("GET", "/", "HTTP/1.1"),
                new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null)));

    }

    @DisplayName("세션 존재 시 기존 세션 사용")
    @Test
    void getSession_usedSession_equal() {
        HttpRequest sameSessionRequest = new HttpRequest(
                new RequestLine("GET", "/", "HTTP/1.1"),
                new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie("JSESSIONID="+sessionId));
        assertThat(sameSessionRequest.getSession().getId()).isEqualTo(sessionId);
    }

    @DisplayName("세션 없을 시 새 세션 생성")
    @Test
    void getSession_newSession_notEqual() {
        HttpRequest newSessionRequest = new HttpRequest(
                new RequestLine("GET", "/", "HTTP/1.1"),
                new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null));
        assertThat(newSessionRequest.getSession().getId()).isNotEqualTo(sessionId);
    }
}