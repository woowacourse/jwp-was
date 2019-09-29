package http.session;

import http.request.HttpCookie;
import http.session.support.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {
    private SessionManager sessionManager;

    @Test
    @DisplayName("SESSIONID가 같을 때 같은 HttpSession을 반환한다")
    public void getSessionWhenEquals() {
        final String sessionIdValue = "24f92f0e-eaad-4da6-9f68-cc019ecdfa37";
        sessionManager = new SessionManager(id -> UUID.fromString(id));

        HttpCookie httpCookie = new HttpCookie(sessionFormat(sessionIdValue));

        HttpSession httpSession1 = sessionManager.getSession(httpCookie);
        HttpSession httpSession2 = sessionManager.getSession(httpCookie);

        assertThat(httpSession1).isEqualTo(httpSession2);
    }

    @Test
    @DisplayName("SESSIONID가 다를 때 서로 다른 HttpSession을 반환한다")
    public void getSessionWhenNotEquals() {
        final String sessionIdValue1 = "24f92f0e-eaad-4da6-9f68-cc019ecdfa37";
        final String sessionIdValue2 = "24f92f0e-eaad-4da6-9f68-cc019ecdfa38";
        sessionManager = new SessionManager(id -> UUID.fromString(id));

        HttpCookie httpCookie1 = new HttpCookie(sessionFormat(sessionIdValue1));
        HttpCookie httpCookie2 = new HttpCookie(sessionFormat(sessionIdValue2));

        HttpSession httpSession1 = sessionManager.getSession(httpCookie1);
        HttpSession httpSession2 = sessionManager.getSession(httpCookie2);

        assertThat(httpSession1).isNotEqualTo(httpSession2);
    }

    @Test
    @DisplayName("SESSIONID가 없을 때 새 SESSIONID를 반환한다")
    public void getNewSession() {
        final String sessionIdValue = "24f92f0e-eaad-4da6-9f68-cc019ecdfa37";
        sessionManager = new SessionManager(id -> UUID.fromString(sessionIdValue));

        HttpCookie httpCookie = new HttpCookie();
        HttpSession httpSession = sessionManager.getSession(httpCookie);

        assertThat(httpSession.getId()).isEqualTo(UUID.fromString("24f92f0e-eaad-4da6-9f68-cc019ecdfa37"));
    }

    private String sessionFormat(final String value) {
        return String.format("%s=%s;", SessionManager.SESSION_NAME, value);
    }
}