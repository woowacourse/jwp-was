package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionsTest {

    @DisplayName("id가 없는 경우 새로운 세션을 생성한다.")
    @Test
    void get1() {
        HttpSession session = HttpSessions.get(null);

        assertThat(session.getId()).isNotNull();
    }

    @DisplayName("id가 있는 경우 새로운 세션을 생성한다.")
    @Test
    void get2() {
        HttpSession session = HttpSessions.get("bebop");

        assertThat(session.getId()).isEqualTo("bebop");
    }
}