package http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SessionManagerTest {

    @Test
    void singleton() {
        assertThat(SessionManager.getInstance() == SessionManager.getInstance()).isTrue();
    }

    @Test
    void getSession_notExistId_canNotBeCreated() {
        SessionManager sessionManager = SessionManager.getInstance();
        String notExistId = "notExistId";

        assertThat(sessionManager.getSession(notExistId, false)).isEqualTo(Optional.empty());
    }

    @Test
    void getSession_notExistId_canBeCreated() {
        SessionManager sessionManager = SessionManager.getInstance();
        String notExistId = "notExistId";

        assertThat(sessionManager.getSession(notExistId, true)).isNotEmpty();
    }

    @Test
    void getSession_existId() {
        Session session = mock(Session.class);
        String existId = "existId";
        SessionManager sessionManager = new SessionManager(new HashMap<String, Session>() {{
            put(existId, session);
        }});

        assertThat(sessionManager.getSession(existId, false).get()).isEqualTo(session);
        assertThat(sessionManager.getSession(existId, true).get()).isEqualTo(session);
    }
}