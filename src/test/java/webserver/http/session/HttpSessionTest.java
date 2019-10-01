package webserver.http.session;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.JSESSIONID_VALUE;

class HttpSessionTest {
    private static final String KEY = "user";
    private static final User USER = new User("test", "1234", "testName", "test@test.com");

    private final Map<String, Object> sessions = new HashMap<>();

    @BeforeEach
    void setUp() {
        sessions.put(KEY, USER);
    }

    @Test
    @DisplayName("Session에 잘들어가는지 검증 테스트")
    void getAttributeTest() {
        HttpSession httpSession = new HttpSession(JSESSIONID_VALUE);
        httpSession.setAttribute(KEY, USER);
        assertThat(httpSession.getAttribute(KEY)).isEqualTo(USER);
    }

    @Test
    @DisplayName("Session Id 테스트")
    void getId() {
        HttpSession httpSession = new HttpSession(JSESSIONID_VALUE);
        assertThat(httpSession.getId()).isEqualTo(JSESSIONID_VALUE);
    }

    @Test
    @DisplayName("Session에 들어가 있는걸 잘 지우는지 테스트")
    void removeTest() {
        HttpSession httpSession = new HttpSession(JSESSIONID_VALUE);
        httpSession.setAttribute(KEY, USER);
        httpSession.removeAttribute(KEY);
        assertThat(httpSession.getAttribute(KEY)).isEqualTo(null);
    }

    @AfterEach
    void tearDown() {
        sessions.clear();
    }
}