package kr.wootecat.dongle.core.handler;

import static java.util.Arrays.*;
import static kr.wootecat.dongle.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.http.request.HttpRequestLine;
import kr.wootecat.dongle.http.request.HttpRequestParameters;

class HandlerMappingsTest {

    @DisplayName("HandlerMappings 가 가지고 있는 HandlerMapping 인스턴스 중, 인자로 받는 request의 처리를 지원하는 HandlerMapping 인스턴스를 찾는다.")
    @Test
    void findHandler() {
        HandlerMapping handlerMapping = new UnsupportedHandlerMapping();
        HandlerMapping secondHandlerMapping = new SupportedHandlerMapping();
        HandlerMappings handlerMappings = new HandlerMappings(asList(handlerMapping, secondHandlerMapping));
        HttpRequest mockRequest = createMockHttpRequest();
        HandlerMapping handler = handlerMappings.findHandler(mockRequest);
        assertThat(handler).isInstanceOf(SupportedHandlerMapping.class);
    }

    private HttpRequest createMockHttpRequest() {
        HttpRequestLine requestLine = new HttpRequestLine(GET, "/test", "HTTP/1.1");
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestParameters requestParameters = new HttpRequestParameters(new HashMap<>());
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}