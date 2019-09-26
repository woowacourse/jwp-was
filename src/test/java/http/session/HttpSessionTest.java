package http.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpSessionTest {
    private SessionManager sessionManager = new HttpSessionManager(() -> "sessionId");
    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = sessionManager.newSession();
    }

    @Test
    void 세션조작() {
        assertThat(session.getId()).isNotEmpty();

        session.setAttribute("some", "value");
        assertThat(session.getAttribute("some")).isEqualTo("value");

        session.removeAttribute("some");
        assertThat(session.getAttribute("some")).isNull();

        User user = User.builder()
                .userId("userId")
                .password("password")
                .name("name")
                .email("email")
                .build();

        session.setAttribute("object", user);
        assertDoesNotThrow(() -> (User) session.getAttribute("object"));

        session.invalidate();
        assertThat(session.getAttribute("object")).isNull();
    }
}