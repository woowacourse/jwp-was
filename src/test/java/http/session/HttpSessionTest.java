package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    @Test
    @DisplayName("현재 세션에 할당되어 있는 고유한 세션 아이디를 반환")
    void getId() {
        HttpSession session = new HttpSession("id");

        assertThat(session.getId()).isEqualTo("id");
    }

    @Test
    @DisplayName("현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장")
    void setAttribute() {
        HttpSession session = new HttpSession("id");
        Object value = new Object();

        session.setAttribute("name", value);

        assertThat(session.getAttribute("name")).isEqualTo(value);
    }
}