package http.session;

import http.request.HttpCookie;
import http.session.support.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {
    private SessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        sessionManager = new SessionManager();
    }

    @Test
    @DisplayName("세션이 없을 때 새로운 세션을 발급한다")
    public void getNewSession() {
        HttpCookie httpCookie = new HttpCookie("SESSIOONID=24f92f0e-eaad-4da6-9f68-cc019ecdfa37;");

        HttpSession httpSession1 = sessionManager.getSession(httpCookie);
        HttpSession httpSession2 = sessionManager.getSession(httpCookie);

        assertThat(httpSession1).isNotEqualTo(httpSession2);
    }
}