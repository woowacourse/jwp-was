package webserver;

import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    @Test
    void 세션_생성_확인() {
        String sessionId = SessionManager.createSession();
        HttpSession session = SessionManager.getSession(sessionId);
        assertThat(session.getSessionId()).isEqualTo(sessionId);
    }
}