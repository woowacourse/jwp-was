package webserver;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionTest {

    private final String id = UUID.randomUUID().toString();

    @DisplayName("세션 ID 반환")
    @Test
    void getId() {
        HttpSession httpSession = new HttpSession(id);

        Assertions.assertThat(httpSession.getId()).isNotNull();
    }

    @DisplayName("세션 value 저장")
    @Test
    void setAttribute() {
        HttpSession httpSession = new HttpSession(id);
        String name = "name";
        Object value = new Object();
        httpSession.setAttribute(name, value);

        Assertions.assertThat(httpSession.getAttribute(name)).isNotNull();
    }

    @DisplayName("세션 value 조회")
    @Test
    void getAttribute() {
        HttpSession httpSession = new HttpSession(id);
        String name = "name";
        Object value = new Object();
        httpSession.setAttribute(name, value);

        Assertions.assertThat(httpSession.getAttribute(name)).isNotNull();
    }

    @DisplayName("세션 value 삭제")
    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession(id);
        String name = "name";
        Object value = new Object();
        httpSession.setAttribute(name, value);
        httpSession.removeAttribute(name);

        Assertions.assertThat(httpSession.isEmpty()).isNotNull();
    }

    @DisplayName("세션에 저장되어 있는 모든 value 삭제")
    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession(id);
        httpSession.setAttribute("name", new Object());

        httpSession.invalidate();

        Assertions.assertThat(httpSession.isEmpty()).isTrue();
    }
}
