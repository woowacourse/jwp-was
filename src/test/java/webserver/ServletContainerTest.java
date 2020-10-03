package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.controller.Controller;
import webserver.controller.UserCreateController;
import webserver.http.request.HttpRequest;

class ServletContainerTest {
    private ServletContainer servletContainer;

    @BeforeEach
    void setUp() {
        servletContainer = new ServletContainer();
    }

    @DisplayName("Http 요청에 해당하는 서블릿의 존재 여부를로 반환한다.")
    @Test
    void hasMappingServlet_shouldReturnTrue() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        boolean hasServlet = servletContainer.hasMappingServlet(httpRequest);

        assertThat(hasServlet).isTrue();
    }

    @DisplayName("정적 컨텐츠에 대한 요청인 경우 서블릿의 존재 여부를 false 반환한다.")
    @Test
    void hasMappingServlet_withRequestForStaticContent_shouldReturnFalse() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfTemplatesResource();

        boolean hasServlet = servletContainer.hasMappingServlet(httpRequest);

        assertThat(hasServlet).isFalse();
    }

    @DisplayName("서블릿이 존재하지 않는 요청인 경우 예외를 던진다.")
    @Test
    void getController_withWrongRequest_shouldThrowException() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfTemplatesResource();

        assertThatThrownBy(() -> servletContainer.getController(httpRequest))
            .isInstanceOf(CannotInitServletException.class)
            .hasMessageContaining(httpRequest.getDefaultPath());
    }

    @DisplayName("Http 요청에 해당하는 Controller(서블릿)을 반환한다.")
    @Test
    void getController_shouldReturnServletController() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        Controller controller = servletContainer.getController(httpRequest);

        assertThat(controller).isInstanceOf(UserCreateController.class);
    }
}