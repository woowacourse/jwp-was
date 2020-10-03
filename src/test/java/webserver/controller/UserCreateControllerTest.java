package webserver.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.HttpRequestFixture;
import webserver.domain.request.HttpRequest;

class UserCreateControllerTest {
    private UserCreateController controller;

    @BeforeEach
    void setUp() {
        controller = new UserCreateController();
    }

    @DisplayName("회원가입에 대한 GET 요청이 들어오면 파라미터의 값으로 회원을 생성한다.")
    @Test
    void get() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        controller.service(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("회원가입 시 파라미터가 빈 값이 있는 경우 예외를 던진다.")
    @Test
    void get_withEmptyName_shouldThrowException() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestWithEmptyParameter();

        assertThatThrownBy(() -> controller.service(httpRequest))
            .isInstanceOf(ApplicationBusinessException.class)
            .hasMessage("회원 가입이 실패하였습니다. 입력하지 않은 정보가 있습니다.");
    }

    @DisplayName("회원가입에 대한 POST 요청이 들어오면 바디의 값으로 회원을 생성한다.")
    @Test
    void post() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfPostMethod();

        controller.service(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("정의하지 않은 PUT 요청이 들어오면 예외를 던진다.")
    @Test
    void put_shouldThrowException() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfPutMethod();

        assertThatThrownBy(() -> controller.service(httpRequest))
            .isInstanceOf(UndefinedHttpRequestMethodException.class)
            .hasMessageContaining(httpRequest.getMethod())
            .hasMessageContaining(httpRequest.getDefaultPath());
    }
}