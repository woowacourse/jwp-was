package kr.wootecat.dongle.model.handler;

import static kr.wootecat.dongle.model.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.model.http.HttpMethod;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.request.HttpRequestBody;
import kr.wootecat.dongle.model.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.model.http.request.HttpRequestLine;
import kr.wootecat.dongle.model.http.request.ProtocolVersion;
import kr.wootecat.dongle.model.http.request.Url;
import kr.wootecat.dongle.model.servlet.ServletMapper;

class ServletHandlerMappingTest {

    @DisplayName("특정 요청이 서블릿 핸들러 매핑으로 지원되는지 테스트")
    @Test
    void isSupport_return_true_when_request_resource_is_not_static() {
        ServletMapper servletMapper = ServletMapper.getInstance();
        HandlerMapping handlerMapping = new ServletHandlerMapping(servletMapper);
        boolean actual = handlerMapping.isSupport(createMockHttpRequest(GET, "/test"));

        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("특정 요청이 서블릿 핸들러 매핑으로 지원되는지 테스트")
    @Test
    void isSupport_return_false_when_request_resource_is_not_static() {
        ServletMapper servletMapper = ServletMapper.getInstance();
        HandlerMapping handlerMapping = new ServletHandlerMapping(servletMapper);
        boolean actual = handlerMapping.isSupport(createMockHttpRequest(GET, "/test.css"));

        assertThat(actual).isEqualTo(false);
    }

    private HttpRequest createMockHttpRequest(HttpMethod method, String path) {
        HttpRequestLine requestLine = new HttpRequestLine(method, Url.from(path), ProtocolVersion.HTTP_1_1);
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestBody requestParameters = HttpRequestBody.empty();
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}