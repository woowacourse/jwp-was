package http.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("세션 값 추가 & 조회")
    @Test
    void getAttribute() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", "sonypark");
        assertThat(session.getAttribute("user1")).isEqualTo("sonypark");
    }

    @DisplayName("세션값 삭제")
    @Test
    void removeAttribute() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", "sonypark");
        session.setAttribute("user2", "kongson");

        session.removeAttribute("user1");

        assertAll(
            () -> assertThat(session.getAttribute("user1")).isNull(),
            () -> assertThat(session.getAttribute("user2")).isNotNull(),
            () -> assertThat(session.getAttribute("user2")).isEqualTo("kongson")
        );
    }

    @DisplayName("세션 초기화")
    @Test
    void invalidate() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", "sonypark");
        session.setAttribute("user2", "kongson");

        session.invalidate();

        assertAll(
            () -> assertThat(session.getAttribute("user1")).isNull(),
            () -> assertThat(session.getAttribute("user2")).isNull()
        );

    }
}
