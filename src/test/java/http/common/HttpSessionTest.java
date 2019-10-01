package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession("abc");
    }

    @Test
    void getId() {
        assertThat(httpSession.getId()).isEqualTo("abc");
    }

    @Test
    void getAttribute() {
        httpSession.setAttribute("name", "conas");
        assertThat(httpSession.getAttribute("name")).isEqualTo("conas");
    }

    @Test
    void removeAttribute() {
        httpSession.setAttribute("name", "conas");
        httpSession.removeAttribute("name");
        assertThat(httpSession.getAttribute("name")).isEqualTo(null);
    }

    @Test
    void invalidate() {
        httpSession.setAttribute("name", "conas");
        httpSession.setAttribute("location","jamsil");
        httpSession.invalidate();
        assertThat(httpSession.getAttribute("name")).isEqualTo(null);
        assertThat(httpSession.getAttribute("location")).isEqualTo(null);
    }
}