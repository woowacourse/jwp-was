package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private static final String SESSION_ID = "id";
    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = new HttpSession(SESSION_ID);
    }

    @Test
    @DisplayName("현재 세션에 할당되어 있는 고유한 세션 아이디를 반환")
    void getId() {
        assertThat(session.getId()).isEqualTo(SESSION_ID);
    }

    @Test
    @DisplayName("현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장")
    void attribute() {
        Object value = new Object();
        session.setAttribute("name", value);

        assertThat(session.getAttribute("name")).isEqualTo(value);
    }

    @Test
    @DisplayName("현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제")
    void removeAttribute() {
        Object value = new Object();
        session.setAttribute("name", value);
        session.removeAttribute("name");

        assertThat(session.getAttribute("name")).isNull();
    }

    @Test
    @DisplayName("현재 세션에 저장되어 있는 모든 값을 삭제")
    void invalidate() {
        session.setAttribute("name1", new Object());
        session.setAttribute("name2", new Object());
        session.invalidate();

        assertThat(session.getAttribute("name1")).isNull();
        assertThat(session.getAttribute("name2")).isNull();
    }
}