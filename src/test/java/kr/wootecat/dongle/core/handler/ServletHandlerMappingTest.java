package kr.wootecat.dongle.core.handler;

import static kr.wootecat.dongle.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.core.servlet.ServletMapper;
import kr.wootecat.dongle.http.HttpMethod;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.request.HttpRequestBody;
import kr.wootecat.dongle.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.http.request.HttpRequestLine;
import kr.wootecat.dongle.http.request.ProtocolVersion;
import kr.wootecat.dongle.http.request.Url;

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