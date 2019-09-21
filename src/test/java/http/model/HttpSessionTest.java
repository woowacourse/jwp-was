package http.model;

import http.session.HttpSession;
import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpSessionTest {
    @Test
    void 세션에_값을_넣고_가져오기() {
        HttpSession httpSession = new HttpSession("id");
        User user = new User("andole", "password", "andole", "andole@andole.com");
        httpSession.setAttribute("user", user);
        assertThat(httpSession.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void 세션_초기화() {
        HttpSession httpSession = new HttpSession("id");
        User user = new User("andole", "password", "andole", "andole@andole.com");
        httpSession.setAttribute("user", user);
        assertThat(httpSession.getAttribute("user")).isEqualTo(user);

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("user")).isNull();
    }
}