package session.model;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpSessionTest {
    @Test
    void setAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", true);

        assertThat((boolean)httpSession.getAttribute("logined")).isTrue();
    }

    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", true);
        httpSession.removeAttribute("logined");

        Map<String, Object> attributes = httpSession.getAttributes();

        assertThat(attributes.containsKey("logined")).isFalse();
    }

    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", true);
        httpSession.invalidate();

        Map<String, Object> attributes = httpSession.getAttributes();

        assertThat(attributes).hasSize(0);
    }
}
