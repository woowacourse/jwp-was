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
    @DisplayName("저장되어있는 세션이 없는 경우 새 세션을 만들어준다.")
    void getNewHttpSession_ifHttpSession_notExists2() {
        String id = "not exsisted";
        HttpSession newSession = sessionManager.getHttpSession(id);

        assertThat(newSession).isNotNull();
    }

    @Test
    @DisplayName("저장되어있는 세션을 되돌려준다.")
    void getHttpSession() {
        sessionManager.getNewHttpSession();
        HttpSession foundSession = sessionManager.getHttpSession(GENERATED_TEST_ID);

        assertThat(foundSession.getId()).isEqualTo(GENERATED_TEST_ID);
    }
}