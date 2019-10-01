package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.headerfields.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HttpResponseTest {
    HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = mock(HttpRequest.class);
    }

    @Test
    @DisplayName("정상적인 HttpResponse 객체를 생성한다.")
    void httpResponse() {
        String input = "text/html; charset=utf-8";
        HttpContentType httpContentType = HttpContentType.of(input).get();

        assertNotNull(HttpResponse.builder(httpContentType)
                .version(HttpVersion.HTTP_1_1)
                .connection(HttpConnection.KEEP_ALIVE)
                .statusCode(HttpStatusCode.OK)
                .body("Hello World")
                .location("/index.html")
                .build());
    }

    @Test
    @DisplayName("2xx HttpResponse를 생성한다.")
    void successByBody() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.successByBody(httpRequest, MimeType.TEXT_HTML, "body");

        assertTrue(response.toString().contains("200 OK"));
    }

    @Test
    @DisplayName("2xx HttpResponse를 생성한다.")
    void successByFilePath() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.successByFilePath(httpRequest, MimeType.TEXT_HTML, "/index.html");

        assertTrue(response.toString().contains("200 OK"));
    }

    @Test
    @DisplayName("Response 형식에 맞게 문자열로 변환한다.")
    void responseToString() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.successByFilePath(httpRequest, MimeType.TEXT_HTML, "/index.html");

        assertTrue(response.toString().length() > 0);
    }

    @Test
    @DisplayName("3xx (redirection) HttpResponse를 생성한다.")
    void redirection() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.redirection(httpRequest, MimeType.TEXT_HTML, "/index.html");

        assertTrue(response.toString().contains("302 FOUND"));
    }

    @Test
    @DisplayName("static 파일에 대한 Response를 생성한다.")
    void responseStaticFiles() {
        HttpPath path = HttpPath.of("/css/styles.css");

        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);
        given(httpRequest.path()).willReturn(path);

        HttpResponse response = HttpResponse.staticFiles(httpRequest);

        assertTrue(response.toString().contains("200 OK"));
    }

    @Test
    @DisplayName("response에 session cookie를 적용한다.")
    void applySessionCookie() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.redirection(httpRequest, MimeType.TEXT_HTML, "/index.html");
        response.applySessionCookie("testSessionId");

        assertTrue(response.toString().contains("testSessionId"));
    }

    @Test
    @DisplayName("response에 login cookie를 적용한다.")
    void applyLoginCookie() {
        given(httpRequest.version()).willReturn(HttpVersion.HTTP_1_1);
        given(httpRequest.connection()).willReturn(HttpConnection.KEEP_ALIVE);

        HttpResponse response = HttpResponse.redirection(httpRequest, MimeType.TEXT_HTML, "/index.html");

        response.applyLoginCookie("sessionId", true);
        assertTrue(response.toString().contains("logined=true"));

        response.applyLoginCookie("sessionId", false);
        assertTrue(response.toString().contains("logined=false"));
    }
}