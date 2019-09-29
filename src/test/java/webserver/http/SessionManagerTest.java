package webserver.http;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.exception.NotExistSessionIdException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class SessionManagerTest {
    HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = mock(HttpSession.class);
    }

    @Test
    @DisplayName("Session Manager를 정상적으로 생성한다.")
    void createSessionManager() {
        assertNotNull(SessionManager.getInstance());
    }

    @Test
    @DisplayName("Session Manager를 이용해서 정상적으로 세션을 생성한다.")
    void createSession() {
        SessionManager sessionManager = SessionManager.getInstance();
        User user = User.of("id", "password", "name", "email");

        String sessionId = sessionManager.setAttribute("user", user);

        assertNotNull(sessionId);
    }

    @Test
    @DisplayName("생성된 세션을 가져온다.")
    void getSession() {
        SessionManager sessionManager = SessionManager.getInstance();
        User user = User.of("id", "password", "name", "email");

        String sessionId = sessionManager.setAttribute("user", user);

        assertNotNull(sessionManager.getSession(sessionId));
    }

    @Test
    @DisplayName("존재하지 않는 세션 ID로 세션을 찾는 경우 예외가 발생한다.")
    void getSessionFail() {
        SessionManager sessionManager = SessionManager.getInstance();

        assertThrows(NotExistSessionIdException.class, () -> sessionManager.getSession("differentKey"));
    }
}