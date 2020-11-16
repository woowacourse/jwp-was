package kr.wootecat.dongle.http.session;

import static java.util.Arrays.*;
import static kr.wootecat.dongle.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.http.Cookie;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.http.request.HttpRequestLine;
import kr.wootecat.dongle.http.request.HttpRequestParameters;
import kr.wootecat.dongle.http.response.HttpResponse;
import kr.wootecat.dongle.utils.IdGenerator;

class SessionValidatorTest {

    @DisplayName("요청에 JSESSIONID 쿠키 값이 존재하지 않는 경우, 새 세션을 생성 후,  response 해당 세션 ID를 SET-COOKIE 헤더에 추가한다.")
    @Test
    void checkRequestSession() {
        IdGenerator idGenerator = () -> "fixedSessionId";
        SessionValidator sessionValidator = new SessionValidator(SessionStorage.ofEmpty(), idGenerator);
        HttpResponse response = HttpResponse.with200Empty();
        sessionValidator.checkRequestSession(createMockHttpRequest("/any-request"), response);
        Map<String, String> responseHeaders = response.getResponseHeaders().getResponseHeaders();

        String actual = responseHeaders.get("Set-Cookie");
        assertThat(actual).isEqualTo("JSESSIONID=fixedSessionId; Path=/");
    }

    @DisplayName("요청에 JSESSIONID 쿠키 값이 존재하는 경우, 기존의 JSESSIONID를 그대로 반환한다.")
    @Test
    void checkRequestSession_containing_session_id() {
        IdGenerator idGenerator = () -> "fixedSessionId";
        SessionValidator sessionValidator = new SessionValidator(SessionStorage.ofEmpty(), idGenerator);
        HttpResponse response = HttpResponse.with200Empty();
        sessionValidator.checkRequestSession(
                createMockHttpRequest("/any-request-2", asList(new Cookie("JSESSIONID", "이미생성됐던세션아이디"))), response);
        Map<String, String> responseHeaders = response.getResponseHeaders().getResponseHeaders();

        assertThat(responseHeaders).isEmpty();
    }

    private HttpRequest createMockHttpRequest(String path) {
        HttpRequestLine requestLine = new HttpRequestLine(GET, path, "HTTP/1.1");
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestParameters requestParameters = new HttpRequestParameters(new HashMap<>());
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }

    private HttpRequest createMockHttpRequest(String path, List<Cookie> cookies) {
        HttpRequestLine requestLine = new HttpRequestLine(GET, path, "HTTP/1.1");
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), cookies);
        HttpRequestParameters requestParameters = new HttpRequestParameters(new HashMap<>());
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}