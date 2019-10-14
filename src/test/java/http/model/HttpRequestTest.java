package http.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String sessionId;
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);
        httpSession = httpRequest.getHttpSession();
        sessionId = httpSession.getId();
    }

    @Test
    void 기존_세션으로_요청_getHttpSession() {
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader("Cookie", "JSESSIONID=" + sessionId);
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);
        HttpSession newSession = httpRequest.getHttpSession();

        assertThat(newSession).isEqualTo(httpSession);
    }

    @Test
    void 세션없이_요청_getHttpSession() {
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);
        HttpSession newSession = httpRequest.getHttpSession();

        assertThat(newSession).isNotEqualTo(httpSession);
    }
}