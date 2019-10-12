package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.exception.EmptyHttpRequestException;
import http.session.HttpSession;
import http.session.SessionManager;
import http.session.TestIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static http.session.TestIdGenerator.GENERATED_TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    @Test
    void HttpRequest_GET_생성() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        HttpHeaders headers = request.getHeaders();

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getBody()).isEqualTo("");
    }

    @Test
    void HttpRequest_POST_생성() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST.txt");
        HttpHeaders headers = request.getHeaders();

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Content-Length")).isEqualTo("39");
        assertThat(headers.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headers.getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getBody()).isEqualTo("userId=woowa&password=password&name=woo");
    }

    @Test
    void Request가_비어있을_때_예외_발생() {
        InputStream in = new ByteArrayInputStream("".getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.getHttpRequest(in)).isInstanceOf(EmptyHttpRequestException.class);
    }

    @Test
    void QueryParams_GET() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        QueryParams queryParams = request.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void QueryParams_POST() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST.txt");
        QueryParams queryParams = request.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    @DisplayName("cookie에 JSESSIONID가 없는 경우 새로운 세션을 만들어준다.")
    void getSession_ifCookie_hasNoSessionId() throws IOException {
        // Given
        SessionManager sessionManager = new SessionManager(new TestIdGenerator());
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Userlist_When_Logout.txt");

        // When
        request.bindTo(sessionManager);
        assertThat(sessionManager.getHttpSession(GENERATED_TEST_ID)).isNull();

        // Then
        HttpSession newSession = request.getSession();
        assertThat(newSession.getId()).isEqualTo(GENERATED_TEST_ID);
    }

    @Test
    @DisplayName("cookie의 JSESSIONID에 해당하는 session이 있으면 해당 session을 반환한다.")
    void getSession_ifCookie_hasJSESSIONID() throws IOException {
        // Given
        SessionManager sessionManager = new SessionManager(new TestIdGenerator());
        HttpSession session = startNewSession(sessionManager);
        String sessionId = session.getId();

        session.setAttribute("savedAttribute", "savedValue");

        // When
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Userlist_When_Logout.txt");

        request.bindTo(sessionManager);
        request.getHeaders().put("Cookie", "JSESSIONID=" + sessionId);
        HttpSession requestSession = request.getSession();

        // Then
        assertThat(requestSession.getId()).isEqualTo(sessionId);
        assertThat(requestSession.getAttribute("savedAttribute")).isEqualTo("savedValue");
    }

    private HttpSession startNewSession(SessionManager sessionManager) throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Userlist_When_Logout.txt");

        request.bindTo(sessionManager);
        return request.getSession();
    }
}