package utils;

import http.SessionContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractorTest {
    private final String requestLine = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    @DisplayName("requestLine으로부터 method 추출 테스트")
    @Test
    void methodFromRequestLine() {
        assertThat(Extractor.methodFromRequestLine(requestLine)).isEqualTo("GET");
    }

    @DisplayName("requestLine으로부터 path 추출 테스트")
    @Test
    void pathFromRequestLine() {
        assertThat(Extractor.pathFromRequestLine(requestLine)).isEqualTo("/user/create");
    }

    @DisplayName("requestLine으로부터 param 추출 테스트")
    @Test
    void paramFromRequestLine() {
        assertThat(Extractor.paramFromRequestLine(requestLine)).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @DisplayName("쿠키로부터 세션 아이디 추출 테스트")
    @Test
    void sessionIdFromCookie() {
        String cookie = String.format("Idea-9e315a38=2ca897d9-c5e6eb82697ff0; _ga=GA1.1.132160; %s=1234", SessionContainer.SESSION_KEY_FOR_COOKIE);

        String sessionId = Extractor.sessionIdFrom(cookie).get();

        assertThat(sessionId).isEqualTo("1234");
    }

    @DisplayName("쿠키로부터 세션 아이디 추출 - 세션 아이디가 없는 경우")
    @Test
    void sessionIdFromCookieWhenNotFoundSessionId() {
        String cookie = "Idea-9e315a38=2ca897d9-b82697ff0; _ga=GA1.1.13212885260;";

        Optional<String> sessionId = Extractor.sessionIdFrom(cookie);

        assertThat(sessionId).isEmpty();
    }
}
