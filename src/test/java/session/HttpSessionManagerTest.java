package session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = HttpSession.create(new FixSessionUtils());
    }

    @Test
    @DisplayName("세션 추가")
    void add() {
        HttpSessionManager.addSession(httpSession);
        assertThat(HttpSessionManager.getSession("1")).isEqualTo(httpSession);
    }

    @Test
    @DisplayName("세션 삭제")
    void remove() {
        HttpSessionManager.addSession(httpSession);
        HttpSessionManager.removeSession("1");

        assertThat(HttpSessionManager.getSession("1")).isNull();
    }

    @Test
    @DisplayName("세션 없을 때 체크")
    void isMappingValidSession1() {
        assertThat(HttpSessionManager.isMappingValidSession("2")).isFalse();
    }

    @Test
    @DisplayName("유효하지 않은 세션")
    void isMappingValidSession2() {
        HttpSessionManager.addSession(httpSession);
        HttpSessionManager.invalidate("1");
        assertThat(HttpSessionManager.isMappingValidSession("1")).isFalse();
    }
}