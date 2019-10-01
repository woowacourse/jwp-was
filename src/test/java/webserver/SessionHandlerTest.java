package webserver;

import http.common.HttpSession;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionHandlerTest {

    @Test
    void 세션_저장_테스트() {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        HttpSession session = new HttpSession();
        String sessionId = session.getId();
        sessionHandler.addSession(sessionId, session);

        assertThat(sessionHandler.getSession(sessionId)).isEqualTo(session);
    }
}