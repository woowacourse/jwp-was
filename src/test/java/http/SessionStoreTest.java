package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStoreTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = SessionStore.getSession(null);
    }

    @Test
    void 세션이_존재_하지_않을_때() {
        assertThat(session).isNotNull();
    }

    @Test
    void 세션이_존재_할_때() {
        Session storeSession = SessionStore.getSession(session.getId());
        assertThat(storeSession.equals(session)).isTrue();
    }

    @Test
    void 세션이_invalid_상태() {
        Session storeSession = SessionStore.getSession(session.getId());
        session.invalidate();
        Session afterInvalidateSession = SessionStore.getSession(storeSession.getId());

        assertThat(storeSession.equals(afterInvalidateSession)).isFalse();
    }
}