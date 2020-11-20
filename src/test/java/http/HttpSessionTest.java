package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @Test
    void constructor() {
        final HttpSession httpSession = new HttpSession();

        assertThat(httpSession.getId()).isNotEmpty();
    }

    @Test
    void getAttribute() {
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("SessionId", "SessionId");

        assertThat(httpSession.getAttribute("SessionId")).isEqualTo("SessionId");
    }

    @Test
    void removeAttribute() {
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("SessionId", "SessionId");

        httpSession.removeAttribute("SessionId");

        assertThat(httpSession.getAttribute("SessionId")).isNull();
    }
}