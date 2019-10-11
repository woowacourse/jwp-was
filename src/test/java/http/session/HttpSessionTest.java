package http.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private static final String SESSION_ID = "SessionId";
    private static final String TEST_ATTRIBUTE_NAME = "attribute";

    private HttpSession session;
    private User user;

    @BeforeEach
    void setUp() {
        session = new HttpSession(SESSION_ID);
        user = new User("userId", "password", "userName", "email@email.com");

        session.setAttribute(TEST_ATTRIBUTE_NAME, user);
    }

    @Test
    @DisplayName("세션의 id를 되돌려준다.")
    void getId() {
        assertThat(session.getId()).isEqualTo(SESSION_ID);
    }

    @Test
    @DisplayName("세션에 저장된 객체를 가져온다.")
    void getAttribute() {
        assertThat(session.getAttribute(TEST_ATTRIBUTE_NAME)).isEqualTo(user);
    }

    @Test
    @DisplayName("세션에 저장된 객체가 없으면 null을 반환한다.")
    void getAttribute_ifAttribute_isNotExists() {
        assertThat(session.getAttribute("not exsists")).isNull();
    }

    @Test
    @DisplayName("세션에 객체를 저장한다.")
    void setAttribute() {
        // given
        String attributeName = "new attribute";
        User user = new User("newUserId", "password", "newName", "new@email.com");

        // when
        session.setAttribute(attributeName, user);

        // then
        User userInSession = (User) session.getAttribute(attributeName);
        assertThat(userInSession.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("세션에서 상태를 제거한다.")
    void removeAttribute() {
        // When
        session.removeAttribute(TEST_ATTRIBUTE_NAME);

        // Then
        assertThat(session.getAttribute(TEST_ATTRIBUTE_NAME)).isNull();
    }

    @Test
    @DisplayName("현재 세션에 저장되어 있는 모든 값을 제거한다.")
    void invalidate() {
        // Given
        String attributeName = "new attribute";
        User user = new User("newUserId", "password", "newUserName", "new@email.com");

        session.setAttribute(attributeName, user);

        // When
        session.invalidate();

        // Then
        assertThat(session.getAttribute(TEST_ATTRIBUTE_NAME)).isNull();
        assertThat(session.getAttribute(attributeName)).isNull();
    }
}