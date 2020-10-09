package jwp.was.webapplicationserver.configure.security;

import static jwp.was.util.Constants.USER_EMAIL;
import static jwp.was.util.Constants.USER_ID;
import static jwp.was.util.Constants.USER_NAME;
import static jwp.was.util.Constants.USER_PASSWORD;
import static jwp.was.webapplicationserver.configure.security.LoginConfigure.ATTRIBUTE_KEY_USER;
import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.webapplicationserver.configure.session.HttpSession;
import jwp.was.webapplicationserver.configure.session.HttpSessionImpl;
import jwp.was.webapplicationserver.configure.session.HttpSessions;
import jwp.was.webapplicationserver.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieTest {

    private static final String SESSION_ID = "sessionId=";

    @DisplayName("SessionId 검증 - True, SessionId가 유효한 경우")
    @Test
    void verifySessionId_ValidSessionId_ReturnTrue() {
        HttpSessions httpSessions = HttpSessions.getInstance();
        HttpSession httpSession = new HttpSessionImpl();
        User user = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
        httpSession.setAttribute(ATTRIBUTE_KEY_USER, user);
        httpSessions.saveSession(httpSession);

        Cookie cookie = new Cookie(SESSION_ID + httpSession.getId());
        assertThat(cookie.verifySessionId()).isTrue();
    }

    @DisplayName("SessionId 검증 - False, SessionId가 유효하지 않은 경우")
    @Test
    void verifySessionId_InvalidSessionId_Success() {
        HttpSessions httpSessions = HttpSessions.getInstance();
        HttpSession httpSession = new HttpSessionImpl();
        httpSessions.saveSession(httpSession);

        Cookie cookie = new Cookie(SESSION_ID + httpSession.getId());

        assertThat(cookie.verifySessionId()).isFalse();
    }

    @DisplayName("SessionId 검증 - False, SessionId가 존재하지 않는 경우")
    @Test
    void verifySessionId_NotExistsSessionId_Success() {
        Cookie cookie = new Cookie(SESSION_ID + "WrongId");

        assertThat(cookie.verifySessionId()).isFalse();
    }

    @DisplayName("SessionId 검증 - False, SessionId가 없는 경우")
    @Test
    void verifySessionId_HasNotSessionId_Success() {
        Cookie cookie = new Cookie("");

        assertThat(cookie.verifySessionId()).isFalse();
    }
}
