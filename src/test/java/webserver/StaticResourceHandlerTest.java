package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.NotStaticResourceRequestException;
import webserver.request.ServletRequest;
import webserver.response.HttpStatusLine;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

class StaticResourceHandlerTest {

    private ServletRequest staticResourceRequest = new ServletRequest(ServletFixture.REQUEST_START_LINE_WITH_HTML,
        ServletFixture.REQUEST_HEADER_WITH_HTML, ServletFixture.REQUEST_BODY);

    private ServletRequest dynamicResourceRequest = new ServletRequest(ServletFixture.REQUEST_START_LINE,
        ServletFixture.REQUEST_HEADER, ServletFixture.REQUEST_BODY);

    private StaticResourceHandler staticResourceHandler = new DefaultStaticResourceHandler();

    @DisplayName("정적 자원 요청인지를 판단한다.")
    @Test
    void isStaticResourceRequest() {
        assertThat(staticResourceHandler.isStaticResourceRequest(staticResourceRequest))
            .isTrue();
    }

    @DisplayName("동적 자원 요청인지를 판단한다.")
    @Test
    void isDynamicResourceRequest() {
        assertThat(staticResourceHandler.isStaticResourceRequest(dynamicResourceRequest))
            .isFalse();
    }

    @DisplayName("정적 자원 요청을 처리한다.")
    @Test
    void handleStaticResource() {
        ServletResponse response = staticResourceHandler.handle(staticResourceRequest);
        assertThat(response.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(response.getView().getViewName()).isEqualTo("/index.html");
    }

    @DisplayName("동적 자원 요청 처리시 예외를 반환한다.")
    @Test
    void handleDynamicResource() {
        assertThatThrownBy(() -> staticResourceHandler.handle(dynamicResourceRequest))
            .isInstanceOf(NotStaticResourceRequestException.class)
            .hasMessage("정적 리소스 요청이 아닙니다.");
    }

}
