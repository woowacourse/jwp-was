package jwp.was.webapplicationserver.configure.session;

import static jwp.was.util.Constants.ATTRIBUTE_KEY_USER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class HttpSessionsTest {

    private HttpSessions httpSessions = HttpSessions.getInstance();

    @DisplayName("User가 있는지 확인 - True, User가 존재하는 SessionId")
    @Test
    void existsUser_SessionIdExistsUser_ReturnTrue() {
        HttpSession httpSession = new HttpSessionImpl();
        httpSession.setAttribute(ATTRIBUTE_KEY_USER, "TEST_USER");
        httpSessions.saveSession(httpSession);

        boolean result = httpSessions.existsUser(httpSession.getId());

        assertThat(result).isTrue();
    }

    @DisplayName("User가 있는지 확인 - False, User가 존재하지 않는 SessionId")
    @Test
    void existsUser_SessionIdNotExistsUser_ReturnFalse() {
        HttpSession httpSession = new HttpSessionImpl();
        httpSessions.saveSession(httpSession);

        boolean result = httpSessions.existsUser(httpSession.getId());

        assertThat(result).isFalse();
    }

    @DisplayName("User가 있는지 확인 - False, 존재하지 않는 SessionId")
    @Test
    void existsUser_NotExistsSessionId_ReturnFalse() {
        HttpSession httpSession = new HttpSessionImpl();
        httpSessions.saveSession(httpSession);

        boolean result = httpSessions.existsUser(httpSession.getId() + 1);

        assertThat(result).isFalse();
    }

    @DisplayName("User가 있는지 확인 - False, SessionId가 null 또는 Empty")
    @ParameterizedTest
    @NullAndEmptySource
    void existsUser_SessionIdNullOrEmpty_ReturnFalse(String sessionId) {
        boolean result = httpSessions.existsUser(sessionId);

        assertThat(result).isFalse();
    }
}
