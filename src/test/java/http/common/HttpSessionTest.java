package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession(UUID.randomUUID());
    }

    @Test
    @DisplayName("httpSession setAttribute(), getAttribute() 테스트")
    void setAttributeTest() {
        httpSession.setAttribute("user", "woowaTech");

        assertThat(httpSession.getAttribute("user")).isEqualTo("woowaTech");
    }

    @Test
    @DisplayName("removeAttribute() 테스트")
    void removeAttributeTest() {
        httpSession.setAttribute("user", "woowaTech");
        httpSession.removeAttribute("user");

        assertThat(httpSession.getAttribute("user")).isEqualTo(null);
    }

    @Test
    @DisplayName("invalidate() 테스트")
    void removeInvalidate() {
        UUID uuid = httpSession.getUuid();
        assertThat(HttpSessionHandler.getSession(uuid)).isEqualTo(null);
    }
}