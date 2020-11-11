package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionsTest {

    @DisplayName("id가 없는 경우 난수를 id로 이용해서 새로운 세션을 생성한다.")
    @Test
    void createNewSessionIfSessionDoesNotExists() {
        HttpSession session = HttpSessions.get(null);

        assertThat(session.getId()).isNotNull();
    }

    @DisplayName("id가 있는 경우 id를 이용해서 새로운 세션을 생성한다.")
    @Test
    void createNewSessionUsingInputSessionId() {
        HttpSession session = HttpSessions.get("bebop");

        assertThat(session.getId()).isEqualTo("bebop");
    }
}