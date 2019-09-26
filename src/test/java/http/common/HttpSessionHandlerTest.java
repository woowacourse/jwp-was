package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionHandlerTest {
    @Test
    @DisplayName("createSession() 테스트")
    void createSessionTest() {
        assertThat(HttpSessionHandler.createSession()).isInstanceOf(HttpSession.class);
    }

    @Test
    @DisplayName("getSession() 테스트")
    void getSession() {
        HttpSession httpSession = HttpSessionHandler.createSession();
        assertThat(HttpSessionHandler.getSession(httpSession.getUuid())).isEqualTo(httpSession);
    }

    @Test
    @DisplayName("removeSession() 테스트")
    void removeSession() {
        HttpSession httpSession = HttpSessionHandler.createSession();
        HttpSessionHandler.removeSession(httpSession.getUuid());
        assertThat(HttpSessionHandler.getSession(httpSession.getUuid())).isEqualTo(null);
    }
}