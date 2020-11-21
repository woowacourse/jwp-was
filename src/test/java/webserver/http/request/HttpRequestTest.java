package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.SessionDataBase;
import webserver.HttpRequestFixture;

class HttpRequestTest {
    @DisplayName("GET 요청에 대한 HttpRequest 객체를 생성한다. ")
    @Test
    void of_shouldGenerateHttpRequest_whenRequestMethodIsGet() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");
        assertThat(httpRequest.getDefaultPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getParameters()).isEqualTo(expectedParameters);
    }

    @DisplayName("GET 요청 내 쿠키 정보를 가진 HttpRequest 객체를 생성한다.")
    @Test
    void of_shouldParseCookie_whenHttpRequestHasCookie() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestByLoginedUser();

        String cookieValue = httpRequest.getCookieValue("logined");

        assertThat(cookieValue).isEqualTo("true");
    }

    @DisplayName("요청 내 JsessionId값이 없으면 새로운 HttpSession 객체로 HttpRequest 객체를 생성한다.")
    @Test
    void of_createHttpSession_whenHttpRequestDoesntHaveJSessionId() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestWithoutJSessionId();

        String sessionId = httpRequest.getSessionId();

        assertThat(SessionDataBase.findHttpSessionById(sessionId).getId()).isEqualTo(sessionId);
    }

    @DisplayName("요청 내 JsessionId값이 있으면 해당하는 HttpSession 객체로 HttpRequest 객체를 생성한다.")
    @Test
    void of_getHttpSession_whenHttpRequestHasJSessionId() throws IOException {
        String sessionId = "c2b240da-cbf7-4f95-b4f6-e12247cf8a2f";
        SessionDataBase.addHttpSession(new HttpSession(sessionId, new ConcurrentHashMap<>()));

        HttpRequest httpRequest = HttpRequestFixture.httpRequestWithJSessionId();

        assertThat(httpRequest.getSessionId()).isEqualTo(sessionId);
    }
}