package http.common;

import http.exception.NotExistSessionValue;
import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpSessionTest {

    @Test
    void setAttribute_테스트() {
        HttpSession session = new HttpSession();
        session.setAttribute("name", "van");
        String name = String.valueOf(session.getAttribute("name"));
        assertThat(name).isEqualTo("van");
    }

    @Test
    void removeAttribute_테스트() {
        HttpSession session = new HttpSession();
        session.setAttribute("name", "van");
        session.removeAttribute("name");
        assertThrows(NotExistSessionValue.class, () -> session.getAttribute("name"));
    }

    @Test
    void invalidate_테스트() {
        HttpSession session = new HttpSession();
        session.setAttribute("name", "van");
        session.setAttribute("user", new User("van", "1", "d", "a@a.a"));
        session.invalidate();
        assertThrows(NotExistSessionValue.class, () -> session.getAttribute("name"));
        assertThrows(NotExistSessionValue.class, () -> session.getAttribute("user"));
    }
}