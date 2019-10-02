package webserver;

import controller.UserController;
import controller.exception.ControllerNotFoundException;
import http.request.HttpRequest;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ControllerMapperTest {
    @Test
    void HttpRequest요청에_맞는_Controller를_반환() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        assertThat(ControllerMapper.map(request)).isInstanceOf(UserController.class);
    }

    @Test
    void HttpRequest요청에_맞는_Controller가_없는_경우_예외처리() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Static.txt");
        assertThatThrownBy(() -> ControllerMapper.map(request))
                .isInstanceOf(ControllerNotFoundException.class);
    }
}
