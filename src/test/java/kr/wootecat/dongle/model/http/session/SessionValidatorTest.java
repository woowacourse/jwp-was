package kr.wootecat.dongle.model.http.session;

import static java.util.Collections.*;
import static kr.wootecat.dongle.model.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.request.HttpRequestBody;
import kr.wootecat.dongle.model.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.model.http.request.HttpRequestLine;
import kr.wootecat.dongle.model.http.request.ProtocolVersion;
import kr.wootecat.dongle.model.http.request.Url;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.utils.IdGenerator;

class SessionValidatorTest {

    @DisplayName("요청에 JSESSIONID 쿠키 값이 존재하지 않는 경우, 새 세션을 생성 후,  response 해당 세션 ID를 SET-COOKIE 헤더에 추가한다.")
    @Test
    void checkRequestSession() {
        IdGenerator idGenerator = () -> "fixedSessionId";
        SessionValidator sessionValidator = new SessionValidator(SessionStorage.ofEmpty(), idGenerator);
        HttpResponse response = HttpResponse.with200Empty();

        sessionValidator.checkRequestSession(createMockHttpRequest("/any-request"), response);
        Cookie cookie = response.getCookie("JSESSIONID");

        assertAll(
                () -> assertThat(cookie.getName()).isEqualTo("JSESSIONID"),
                () -> assertThat(cookie.getValue()).isEqualTo("fixedSessionId"),
                () -> assertThat(cookie.getPath()).isEqualTo("/")
        );
    }

    @DisplayName("요청에 JSESSIONID 쿠키 값이 존재하는 경우, 기존의 JSESSIONID를 그대로 반환한다.")
    @Test
    void checkRequestSession_containing_session_id() {
        IdGenerator idGenerator = () -> "fixedSessionId";
        SessionValidator sessionValidator = new SessionValidator(SessionStorage.ofEmpty(), idGenerator);
        HttpResponse response = HttpResponse.with200Empty();
        sessionValidator.checkRequestSession(
                createMockHttpRequest("/any-request-2",
                        singletonList(new Cookie("JSESSIONID", "이미생성됐던세션아이디"))), response);
        Map<String, String> responseHeaders = response.getResponseHeaders().getResponseHeaders();

        assertThat(responseHeaders).isEmpty();
    }

    private HttpRequest createMockHttpRequest(String path) {
        HttpRequestLine requestLine = new HttpRequestLine(GET, Url.from(path), ProtocolVersion.HTTP_1_1);
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), new ArrayList<>());
        HttpRequestBody requestParameters = HttpRequestBody.empty();
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }

    private HttpRequest createMockHttpRequest(String path, List<Cookie> cookies) {
        HttpRequestLine requestLine = new HttpRequestLine(GET, Url.from(path), ProtocolVersion.HTTP_1_1);
        HttpRequestHeaders requestHeaders = new HttpRequestHeaders(new HashMap<>(), cookies);
        HttpRequestBody requestParameters = HttpRequestBody.empty();
        return new HttpRequest(requestLine, requestHeaders,
                requestParameters);
    }
}