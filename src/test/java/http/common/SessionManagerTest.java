package http.common;

import http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    @Test
    public void sendSessionTest() {
        HttpResponse httpResponse = HttpResponse.of();
        HttpSession httpSession = SessionManager.createSession();
        SessionManager.sendSession(httpResponse, httpSession);

        assertThat(httpResponse.toString()).contains("JSESSIONID=" + httpSession.getId());
    }
}