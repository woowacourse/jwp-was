package webserver;

import controller.LoginController;
import controller.UserController;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {
    @Test
    @DisplayName("User관련 요청이 들어오면 UserController를 반환한다.")
    void map_UserController_ifUserRequest() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        assertThat(ControllerMapper.map(request)).isInstanceOf(UserController.class);
    }

    @Test
    @DisplayName("Login 관련 요청이 들어오면 LoginController를 반환한다.")
    void map_LoginContrller_ifLoginRequest() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST_Login_Success.txt");
        assertThat(ControllerMapper.map(request)).isInstanceOf(LoginController.class);
    }

    @Test
    void ControllerMapper가_요청을_처리할_수_있는지_확인() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_StaticResource.txt");
        assertThat(ControllerMapper.canHandle(request)).isFalse();
    }
}
