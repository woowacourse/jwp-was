package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTableTest {
    private static final String DEFAULT_ID = "1";

    private SessionIdStrategy testStrategy;

    @BeforeEach
    void setUp() {
        testStrategy = () -> DEFAULT_ID;
        HttpSessionTable.getSession(DEFAULT_ID, testStrategy);
    }

    @Test
    @DisplayName("sessionId에 해당하는 session 반환")
    void getExistSession() {
        HttpSession session = HttpSessionTable.getSession(DEFAULT_ID, testStrategy);

        assertThat(session.getId()).isEqualTo(DEFAULT_ID);
    }

    @Test
    @DisplayName("Session table에 없는 sessionId로 getSession하는 경우 새로 생성")
    void getNotExistSession() {
        HttpSession session = HttpSessionTable.getSession("2", testStrategy);

        assertThat(session.getId()).isEqualTo(DEFAULT_ID);
    }

    @Test
    @DisplayName("sessionId가 null인 경우 새로 생성")
    void getSessionWithNull() {
        HttpSession session = HttpSessionTable.getSession(null, testStrategy);

        assertThat(session.getId()).isEqualTo(DEFAULT_ID);
    }
}