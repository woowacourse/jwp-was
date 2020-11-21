package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;

class HttpSessionTest {
    @DisplayName("HttpSession을 생성한다.")
    @Test
    void create() {
        HttpSession httpSession = HttpSession.create();

        assertThat(httpSession).isNotNull();
    }

    @DisplayName("HttpSession의 속성을 저장한다.")
    @Test
    void setAttribute() {
        HttpSession httpSession = HttpSession.create();
        User user = new User("mozzi", "1234", "moonmozzi", "mozzi@wooteco");

        httpSession.setAttribute("user", user);

        assertThat(httpSession.getAttribute("user")).isEqualTo(user);
    }

    @DisplayName("HttpSession의 속성이름에 해당하는 속성값을 조회한다.")
    @Test
    void getAttribute() {
        HttpSession httpSession = HttpSession.create();
        User user = new User("mozzi", "1234", "moonmozzi", "mozzi@wooteco");

        httpSession.setAttribute("user", user);
        httpSession.setAttribute("food", "chicken");

        assertThat(httpSession.getAttribute("user")).isEqualTo(user);
        assertThat(httpSession.getAttribute("food")).isEqualTo("chicken");
    }

    @DisplayName("HttpSession의 속성이름에 해당하는 속성값을 삭제한다.")
    @Test
    void removeAttribute() {
        HttpSession httpSession = HttpSession.create();
        User user = new User("mozzi", "1234", "moonmozzi", "mozzi@wooteco");
        httpSession.setAttribute("user", user);

        httpSession.removeAttribute("user");

        assertThat(httpSession.getAttribute("user")).isNull();
    }

    @DisplayName("HttpSession의 모든 속성을 삭제한다.")
    @Test
    void invalidate() {
        HttpSession httpSession = HttpSession.create();
        User user = new User("mozzi", "1234", "moonmozzi", "mozzi@wooteco");
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("food", "chicken");

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("user")).isNull();
        assertThat(httpSession.getAttribute("food")).isNull();
    }
}