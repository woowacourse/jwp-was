package http.session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {
    public static final String SESSION_ID = "sessionid";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private Session session = new Session(SESSION_ID);

    @BeforeEach
    void setUp() {
        session.setAttribute(NAME, VALUE);
    }

    @Test
    void getAttributeTest() {
        String nonExistentName = "noooo";
        assertThat(session.getAttribute(NAME)).isEqualTo(VALUE);
        assertThat(session.getAttribute(nonExistentName)).isNull();
    }

    @Test
    void removeAttributeTest() {
        assertThat(session.getAttribute(NAME)).isEqualTo(VALUE);
        session.removeAttribute(NAME);
        assertThat(session.getAttribute(NAME)).isNull();
    }

    @AfterEach
    void tearDown() {
        session.invalidate();
    }
}