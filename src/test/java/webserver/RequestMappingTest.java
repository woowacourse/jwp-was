package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.controller.UserCreateController;
import webserver.http.request.HttpRequest;

class RequestMappingTest {
    private RequestMapping requestMapping;

    @Mock
    private ServletContainer servletContainer;

    @Mock
    private StaticController staticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        requestMapping = new RequestMapping(servletContainer, staticController);
    }

    @DisplayName("요청에 해당하는 서블릿이 있을 경우 해당 Controller(서블릿)을 반환한다.")
    @Test
    void getController_shouldReturnServletController() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();
        given(servletContainer.hasMappingServlet(any(HttpRequest.class))).willReturn(true);
        given(servletContainer.getController(any(HttpRequest.class))).willReturn(new UserCreateController());

        Controller controller = requestMapping.getController(httpRequest);

        assertThat(controller).isInstanceOf(UserCreateController.class);
    }

    @DisplayName("정적 컨텐츠에 대한 요청일 경우 StaticController를 반환한다.")
    @Test
    void getController_shouldReturnStaticController() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfTemplatesResource();
        given(servletContainer.hasMappingServlet(any(HttpRequest.class))).willReturn(false);
        given(staticController.isForStaticContent(any(HttpRequest.class))).willReturn(true);

        Controller controller = requestMapping.getController(httpRequest);

        assertThat(controller).isInstanceOf(StaticController.class);
    }

    @DisplayName("지원하지 않는 요청인 경우 예외를 던진다.")
    @Test
    void getController_shouldThrowException() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfTemplatesResource();
        given(servletContainer.hasMappingServlet(any(HttpRequest.class))).willReturn(false);
        given(staticController.isForStaticContent(any(HttpRequest.class))).willReturn(false);

        assertThatThrownBy(() -> requestMapping.getController(httpRequest))
            .isInstanceOf(UnsupportedClientUrlException.class)
            .hasMessageContaining(httpRequest.getDefaultPath());
    }
}