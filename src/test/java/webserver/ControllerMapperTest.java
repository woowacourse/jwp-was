package webserver;

import controller.LoginController;
import controller.UserController;
import controller.exception.ControllerNotFoundException;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void HttpRequest요청에_맞는_Controller가_없는_경우_예외처리() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Static.txt");
        assertThatThrownBy(() -> ControllerMapper.map(request))
                .isInstanceOf(ControllerNotFoundException.class);
    }
}
