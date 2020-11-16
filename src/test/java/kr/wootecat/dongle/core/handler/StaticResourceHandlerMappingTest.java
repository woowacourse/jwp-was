package kr.wootecat.dongle.core.handler;

import static kr.wootecat.dongle.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.http.HttpMethod;
import kr.wootecat.dongle.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.http.exception.NotFoundException;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.http.request.HttpRequestLine;
import kr.wootecat.dongle.http.request.HttpRequestParameters;
import kr.wootecat.dongle.http.response.HttpResponse;

class StaticResourceHandlerMappingTest {

    @DisplayName("확장자가 있는 특정 정적 자원의 요청이 정적 자원 핸들러 매핑에서 처리를 지원한다.")
    @Test
    void isSupport_return_true_when_request_resource_is_not_static() {
        HandlerMapping handlerMapping = new StaticResourceHandlerMapping();
        boolean actual = handlerMapping.isSupport(createMockHttpRequest(GET, "/test.html"));

        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("확장자가 없는 경로의 요청은 정적 자원 핸들러 매핑에서 처리를 지원하지 않는다.")
    @Test
    void isSupport_return_false_when_request_resource_is_not_static() {
        HandlerMapping handlerMapping = new StaticResourceHandlerMapping();
        boolean actual = handlerMapping.isSupport(createMockHttpRequest(GET, "/test"));

        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("정적 자원 요청의 HTTP 메서드가 GET이 아닌 경우, MethodNotAllowedException 발생")
    @Test
    void handle_throw_exception_when_request_method_type_is_not_get_type() {
        HandlerMapping handlerMapping = new StaticResourceHandlerMapping();
        HttpRequest noneGetHttpRequest = createMockHttpRequest(POST, "/resource.html");
        HttpResponse httpResponse = HttpResponse.with200Empty();
        assertThatThrownBy(() -> handlerMapping.handle(noneGetHttpRequest, httpResponse))
                .isInstanceOf(MethodNotAllowedException.class)
                .hasMessageContaining("타입 메서드는 지원하지 않습니다.");
    }

    @DisplayName("요청하는 정적 자원이 서버에서 지원하지 않는 타입인 경우, NotFountException 발생")
    @Test
    void handle_throw_exception_when_request_resource_type_is_not_support() {
        HandlerMapping handlerMapping = new StaticResourceHandlerMapping();
        HttpRequest unsupportedFileTypeRequest = createMockHttpRequest(GET,
                "/kr/wootecat/dongle/core/handler/HandlerMapping.java");
        HttpResponse httpResponse = HttpResponse.with200Empty();
        assertThatThrownBy(() -> handlerMapping.handle(unsupportedFileTypeRequest, httpResponse))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("다음 경로의 요청에 해당하는 리소스가 존재하지 않습니다.");
    }

    @DisplayName("요청하는 정적 자원이 서버에 존재하지 않는 경우, NotFountException 발생")
    @Test
    void handle_throw_exception_when_request_resource_is_not_exist() {
        HandlerMapping handlerMapping = new StaticResourceHandlerMapping();
        HttpRequest noneExistFileRequest = createMockHttpRequest(GET,
                "/user/not/existfile.html");
        HttpResponse httpResponse = HttpResponse.with200Empty();
        assertThatThrownBy(() -> handlerMapping.handle(noneExistFileRequest, httpResponse))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("다음 경로의 요청에 해당하는 리소스가 존재하지 않습니다.");
    }

    private HttpRequest createMockHttpRequest(HttpMethod method, String path) {
        HttpRequestLine requestLine = new HttpRequestLine(method, path, "HTTP/1.1");
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestParameters requestParameters = new HttpRequestParameters(new HashMap<>());
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}