package http.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;

class HttpSessionTest {

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("sony", "pw123", "sonypark", "sonypark@sony.com");
        user2 = new User("park", "pw1234", "kongson", "park@kongson.com");
    }

    @DisplayName("세션 값 추가 & 조회")
    @Test
    void getAttribute() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", user1);
        assertThat(session.getAttribute("user1")).isEqualTo(user1);
    }

    @DisplayName("세션값 삭제")
    @Test
    void removeAttribute() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", user1);
        session.setAttribute("user2", user2);

        session.removeAttribute("user1");

        assertAll(
            () -> assertThat(session.getAttribute("user1")).isNull(),
            () -> assertThat(session.getAttribute("user2")).isNotNull(),
            () -> assertThat(session.getAttribute("user2")).isEqualTo(user2)
        );
    }

    @DisplayName("세션 초기화")
    @Test
    void invalidate() {
        HttpSession session = new HttpSession("id");
        session.setAttribute("user1", user1);
        session.setAttribute("user2", user2);

        session.invalidate();

        assertAll(
            () -> assertThat(session.getAttribute("user1")).isNull(),
            () -> assertThat(session.getAttribute("user2")).isNull()
        );

    }
}
