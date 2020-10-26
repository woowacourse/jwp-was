package webserver.login;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceTest {
    private static final String ID = "myId";
    private static final String PASSWORD = "myPassword";
    private static LoginService loginService = new LoginService();

    @BeforeEach
    private void setUp() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    public void loginTest() {
        DataBase.addUser(new User(ID, PASSWORD, "myName", "my@email.com"));

        boolean result = loginService.login(ID, PASSWORD);
        assertThat(result).isTrue();
    }

    @DisplayName("로그인 실패: 존재하지 않는 회원일 때 ")
    @Test
    public void loginFailTest_NoUser() {
        boolean result = loginService.login(ID, PASSWORD);

        assertThat(result).isFalse();
    }

    @DisplayName("로그인 실패: 잘못된 비밀번호")
    @Test
    public void loginFailTest_WrongPassword() {
        DataBase.addUser(new User(ID, PASSWORD, "myName", "my@email.com"));

        final String WRONG_PASSWORD = "wrongPassword";
        boolean result = loginService.login(ID, WRONG_PASSWORD);

        assertThat(result).isFalse();
    }
}