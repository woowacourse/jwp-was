package mvc.controller.impl;

import mvc.db.DataBase;
import mvc.exception.LoginException;
import mvc.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginControllerTest extends RequestHelper {
    private static final String NOT_FOUND_USER_ID_MESSAGE = "해당 사용자가 존재하지 않습니다.";
    private static final String UNMATCHED_USER_MESSAGE = "비밀번호가 일치하지 않습니다.";

    private Request request;
    private LoginController loginController;


    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        this.request = new Request(ioUtils(requestPostWithQuery));
        this.loginController = new LoginController();
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        DataBase.addUser(new User("javajigi", "password", "포비", "pobi@pobi.com"));

        assertThat(loginController.doPost(request).toBytes())
                .isEqualTo(new Response.Builder().redirectUrl("/").build().toBytes());
    }

    @Test
    @DisplayName("존재하지 않는 userId로 로그인할 때 예외처리")
    void loginException1() {
        LoginException thrown = assertThrows(LoginException.class, () -> loginController.doPost(request));
        assertEquals(thrown.getMessage(), NOT_FOUND_USER_ID_MESSAGE);
    }

    @Test
    @DisplayName("비밀번호가 틀렸을 때 예외처리")
    void loginException2() {
        DataBase.addUser(new User("javajigi", "1234", "포비", "pobi@pobi.com"));

        LoginException thrown = assertThrows(LoginException.class, () -> loginController.doPost(request));
        assertEquals(thrown.getMessage(), UNMATCHED_USER_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }
}