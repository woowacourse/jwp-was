package http.session;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    @Test
    public void getId() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.newInstance(uuid);

        assertThat(httpSession.getId()).isEqualTo(uuid);
    }

    @Test
    public void getAttribute() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.newInstance(uuid);

        httpSession.setAttribute("isLogin", "true");
        assertThat((String) httpSession.getAttribute("isLogin")).isEqualTo("true");
    }

    @Test
    public void removeAttribute() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.newInstance(uuid);

        httpSession.setAttribute("isLogin", "true");
        httpSession.removeAttribute("isLogin");
        assertThat((String) httpSession.getAttribute("isLogin")).isNull();
    }

    @Test
    public void invalidate() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.newInstance(uuid);

        httpSession.setAttribute("test1", "test1");
        httpSession.setAttribute("test2", "test2");
        httpSession.invalidate();

        assertThat((String) httpSession.getAttribute("test1")).isNull();
        assertThat((String) httpSession.getAttribute("test2")).isNull();
    }
}