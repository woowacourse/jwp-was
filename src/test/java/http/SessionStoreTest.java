package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStoreTest {
    @Test
    void 세션이_존재_하지_않을_때() {
        Session session = SessionStore.getSession(null);
        assertThat(session).isNotNull();
    }

    @Test
    void 세션이_존재_할_때() {
        Session session = SessionStore.getSession(null);
        Session storeSession = SessionStore.getSession(session.getId());
        assertThat(storeSession.equals(session)).isTrue();
    }
}