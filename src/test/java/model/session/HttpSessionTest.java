package model.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HttpSessionTest {

    private static final String SESSION_ID = "1";
    private static final String ATTRIBUTE_KEY = "key";
    private static final String ATTRIBUTE_VALUE = "value";

    @Test
    void getId() {
        HttpSession httpSession = new HttpSession(SESSION_ID);

        assertThat(httpSession.getId()).isEqualTo(SESSION_ID);
    }

    @Test
    void setAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);

        assertThat(httpSession.isEmpty()).isFalse();
    }

    @Test
    void getAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);

        assertThat(httpSession.getAttribute(ATTRIBUTE_KEY)).isEqualTo(ATTRIBUTE_VALUE);
    }

    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
        httpSession.removeAttribute(ATTRIBUTE_KEY);

        assertThat(httpSession.isEmpty()).isTrue();
    }

    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
        httpSession.invalidate();

        assertThat(httpSession.isEmpty()).isTrue();
    }

    @Test
    void isEmpty() {
        HttpSession httpSession = new HttpSession(SESSION_ID);

        assertThat(httpSession.isEmpty()).isTrue();
    }
}