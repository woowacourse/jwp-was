package session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = HttpSession.create(new FixSessionUtils());
    }

    @Test
    void create() {
        assertThat(httpSession.getId()).isEqualTo("1");
        assertThat(httpSession.isValid()).isTrue();
    }

    @Test
    void setAttribute() {
        httpSession.setAttribute("key", "value");
        assertThat(httpSession.getAttribute("key")).isEqualTo("value");
    }

    @Test
    void removeAttribute() {
        httpSession.setAttribute("key", "value");
        httpSession.removeAttribute("key");
        assertThat(httpSession.getAttribute("key")).isNull();
    }

    @Test
    void invalidate() {
        httpSession.setAttribute("key", "value");
        httpSession.invalidate();
        assertThat(httpSession.getAttribute("key")).isNull();
        assertThat(httpSession.isValid()).isFalse();
    }
}