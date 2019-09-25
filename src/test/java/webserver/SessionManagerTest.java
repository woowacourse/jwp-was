package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    @Test
    void create_session() {
        HttpSession session = SessionManager.getSession(null);
        assertThat(session).isNotNull();
    }

    @Test
    void create_with_invalid_session() {
        HttpSession session = SessionManager.getSession(null);
        session.invalidate();
        HttpSession newSession = SessionManager.getSession(session.getId());
        assertThat(newSession).isNotEqualTo(session);
    }
}
