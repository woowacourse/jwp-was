package http;

import http.exception.NotFoundSessionAttributeException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionTest {

    @Test
    void getId() {
        String id = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(id);
        assertThat(httpSession.getId()).isEqualTo(id);
    }

    @Test
    void addAttribute() {
        String id = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(id);
        httpSession.addAttribute("test", "zino");
        assertThat(httpSession.getAttributes("test")).isEqualTo("zino");
    }

    @Test
    void removeAttribute() {
        String id = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(id);
        httpSession.addAttribute("test", "zino");
        httpSession.removeAttribute("test");

        assertThrows(NotFoundSessionAttributeException.class, () -> httpSession.getAttributes("test"));
    }

    @Test
    void invalidate() {
        String id = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(id);
        httpSession.addAttribute("test", "zino");
        httpSession.invalidate();

        assertThrows(NotFoundSessionAttributeException.class, () -> httpSession.getAttributes("test"));
    }
}