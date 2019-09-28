package webserver.http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.exception.NonexistentSessionValue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionTest {
    @Test
    @DisplayName("정상적으로 세션을 생성한다.")
    void createHttpSession() {
        HttpSession session = new HttpSession();
        assertNotNull(session);
    }

    @Test
    @DisplayName("세션의 아이디를 가져온다.")
    void getSessionId() {
        HttpSession session = new HttpSession();
        assertTrue(session.getId().length() > 0);
    }

    @Test
    @DisplayName("세션에 값을 저장한다.")
    void setAttribute() {
        User user = User.of("id", "password", "test", "email");
        HttpSession session = new HttpSession();
        
        session.setAttribute("user", user);
        
        assertThat(session.getAttribute("user")).isEqualTo(user);
    }

    @Test
    @DisplayName("세션의 없는 값을 가져오는 경우 예외가 발생한다.")
    void getAttributeFail() {
        HttpSession session = new HttpSession();

        assertThrows(NonexistentSessionValue.class, () -> session.getAttribute("no"));
    }

    @Test
    @DisplayName("해당하는 세션에 있는 값을 삭제한다.")
    void removeAttribute() {
        User user = User.of("id", "password", "test", "email");
        HttpSession session = new HttpSession();

        session.setAttribute("user", user);
        assertThat(session.getAttribute("user")).isEqualTo(user);
        session.removeAttribute("user");
        assertThrows(NonexistentSessionValue.class, () -> session.getAttribute("user"));
    }

    @Test
    @DisplayName("세션에 없는 값을 삭제하는 경우 예외가 발생한다.")
    void removeAttributeFail() {
        HttpSession session = new HttpSession();
        assertThrows(NonexistentSessionValue.class, () -> session.removeAttribute("nonexistence user"));
    }

    @Test
    @DisplayName("세션에 있는 값들을 모두 삭제한다.")
    void invalidateSession() {
        User user1 = User.of("id", "password", "test1", "email");
        User user2 = User.of("id", "password", "test2", "email");

        HttpSession session = new HttpSession();
        session.setAttribute("user1", user1);
        session.setAttribute("user2", user2);

        session.invalidate();

        assertThrows(NonexistentSessionValue.class, () -> {
           session.getAttribute("user1");
           session.getAttribute("user2");
        });
    }
}