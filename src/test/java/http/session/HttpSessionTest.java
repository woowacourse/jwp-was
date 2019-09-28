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
}