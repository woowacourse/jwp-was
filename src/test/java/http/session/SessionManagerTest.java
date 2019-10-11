package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.session.TestIdGenerator.GENERATED_TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager(new TestIdGenerator());
    }

    @Test
    @DisplayName("새 세션을 만들어준다.")
    void getNewHttpSession_ifHttpSession_notExists() {
        HttpSession newSession = sessionManager.getNewHttpSession();

        assertThat(newSession).isNotNull();
    }

    @Test
    @DisplayName("저장되어있는 세션을 되돌려준다.")
    void getHttpSession() {
        sessionManager.getNewHttpSession();
        HttpSession foundSession = sessionManager.getHttpSession(GENERATED_TEST_ID);

        assertThat(foundSession.getId()).isEqualTo(GENERATED_TEST_ID);
    }

    @Test
    @DisplayName("저장되어있는 세션을 찾지 못한 경우 null을 되돌려준다.")
    void getHttpSession_IfSession_isNotExsist() {
        HttpSession foundSession = sessionManager.getHttpSession("not exists id");

        assertThat(foundSession).isNull();
    }
}