package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTableTest {
    private SessionIdStrategy testStrategy;
    private static final String DEFAULT_ID = "1";

    @BeforeEach
    void setUp() {
        testStrategy = () -> DEFAULT_ID;
    }

    @Test
    @DisplayName("sessionId에 해당하는 session 반환")
    void getExistSession() {
        HttpSession session = HttpSessionTable.getSession(DEFAULT_ID, testStrategy);

        assertThat(HttpSessionTable.getSession(DEFAULT_ID, testStrategy)).isEqualTo(session);
    }

    @Test
    @DisplayName("Session table에 없는 sessionId로 getSession하는 경우 새로 생성")
    void getNotExistSession() {
        HttpSession session = HttpSessionTable.getSession(DEFAULT_ID, testStrategy);

        assertThat(HttpSessionTable.getSession("2", testStrategy)).isNotEqualTo(session);
    }
}