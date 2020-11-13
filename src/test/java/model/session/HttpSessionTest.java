package model.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    private static final String SESSION_ID = "1";
    private static final String ATTRIBUTE_KEY = "key";
    private static final String ATTRIBUTE_VALUE = "value";

    @Test
    @DisplayName("SessionID 확인")
    void getId() {
        HttpSession httpSession = new HttpSession(SESSION_ID);

        assertThat(httpSession.getId()).isEqualTo(SESSION_ID);
    }

    @Test
    @DisplayName("Attribute 추가")
    void setAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);

        assertThat(httpSession.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Attribute 조회")
    void getAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);

        assertThat(httpSession.getAttribute(ATTRIBUTE_KEY)).isEqualTo(ATTRIBUTE_VALUE);
    }

    @Test
    @DisplayName("Attribute 제거")
    void removeAttribute() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
        httpSession.removeAttribute(ATTRIBUTE_KEY);

        assertThat(httpSession.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Attribute 초기화")
    void invalidate() {
        HttpSession httpSession = new HttpSession(SESSION_ID);
        httpSession.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
        httpSession.invalidate();

        assertThat(httpSession.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Attribute 비어있는지 확인")
    void isEmpty() {
        HttpSession httpSession = new HttpSession(SESSION_ID);

        assertThat(httpSession.isEmpty()).isTrue();
    }
}