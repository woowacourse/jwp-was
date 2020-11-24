package kr.wootecat.dongle.model.handler;

import static java.util.Arrays.*;
import static kr.wootecat.dongle.model.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.request.HttpRequestBody;
import kr.wootecat.dongle.model.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.model.http.request.HttpRequestLine;
import kr.wootecat.dongle.model.http.request.ProtocolVersion;
import kr.wootecat.dongle.model.http.request.Url;

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
        HttpRequestLine requestLine = new HttpRequestLine(GET, Url.from("/test"), ProtocolVersion.HTTP_1_1);
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestBody requestParameters = HttpRequestBody.empty();
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}