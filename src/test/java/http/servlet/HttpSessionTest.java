package http.servlet;

import http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private String sessionId;
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        sessionId = "sessionId";
        httpSession = new HttpSession(sessionId);
    }

    @DisplayName("세션 attribute 추가 테스트")
    @Test
    void setAttribute() {
        httpSession.setAttribute("userName", "abcde");

        Map<String, String> attributes = httpSession.getAttributes();

        assertThat(attributes).containsKeys("userName");
    }

    @DisplayName("세션 attribute 불러오기 테스트")
    @Test
    void getAttribute() {
        httpSession.setAttribute("userName", "abcde");

        assertThat(httpSession.getAttribute("userName")).isEqualTo("abcde");
    }

    @DisplayName("세션 attribute 삭제 테스트")
    @Test
    void removeAttribute() {
        httpSession.setAttribute("userName", "abcde");

        httpSession.removeAttribute("userName");

        Map<String, String> attributes = httpSession.getAttributes();
        assertThat(attributes).isEmpty();
    }

    @DisplayName("세션의 모든 attribute를 삭제 테스트")
    @Test
    void invalidate() {
        httpSession.setAttribute("userName", "abcde");
        httpSession.setAttribute("phoneNumber", "01012345678");

        httpSession.invalidate();

        Map<String, String> attributes = httpSession.getAttributes();
        assertThat(attributes).isEmpty();
    }
}
