package jwp.was.webapplicationserver.model;

import static jwp.was.util.Constants.EMPTY;
import static jwp.was.util.Constants.USER_EMAIL;
import static jwp.was.util.Constants.USER_ID;
import static jwp.was.util.Constants.USER_NAME;
import static jwp.was.util.Constants.USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("생성자 테스트")
    @Test
    void constructor_Success() {
        User user = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);

        assertThat(user.getUserId()).isEqualTo(USER_ID);
        assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(user.getName()).isEqualTo(USER_NAME);
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
    }

    @DisplayName("생성자 테스트, 예외 발생 - UserId가 NULL")
    @Test
    void constructor_NullUserId_ThrownException() {
        assertThatThrownBy(() -> new User(null, USER_PASSWORD, USER_NAME, USER_EMAIL))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - PASSWORD가 NULL")
    @Test
    void constructor_NullPassword_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, null, USER_NAME, USER_EMAIL))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - NAME이 NULL")
    @Test
    void constructor_NullName_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, USER_PASSWORD, null, USER_EMAIL))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - EMAIL이 NULL")
    @Test
    void constructor_NullEmail_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, USER_PASSWORD, USER_NAME, null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - UserId가 EMPTY")
    @Test
    void constructor_EmptyUserId_ThrownException() {
        assertThatThrownBy(() -> new User(EMPTY, USER_PASSWORD, USER_NAME, USER_EMAIL))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - PASSWORD가 Empty")
    @Test
    void constructor_EmptyPassword_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, EMPTY, USER_NAME, USER_EMAIL))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - NAME이 Empty")
    @Test
    void constructor_EmptyName_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, USER_PASSWORD, EMPTY, USER_EMAIL))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성자 테스트, 예외 발생 - EMAIL이 Empty")
    @Test
    void constructor_EmptyEmail_ThrownException() {
        assertThatThrownBy(() -> new User(USER_ID, USER_PASSWORD, USER_NAME, EMPTY))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
