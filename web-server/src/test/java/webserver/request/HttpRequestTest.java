package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.TestIOUtils.createRequestBufferedReader;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpSession;
import webserver.HttpSessions;

public class HttpRequestTest {

    @DisplayName("HttpRequest GET 요청")
    @Test
    void request_GET() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_GET.txt"));

        assertAll(() -> {
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(request.getPath()).isEqualTo("/user/create");
            assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
            assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        });
    }

    @DisplayName("HttpRequest POST 요청, Param 없음")
    @Test
    void request_POST_WithoutParam() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_POST_Create_User.txt"));

        assertAll(() -> {
            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getPath()).isEqualTo("/user/create");
            assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
            assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi");
        });
    }

    @DisplayName("HttpRequest POST 요청, Param 있음")
    @Test
    void request_POST_WithParam() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_POST_With_Param.txt"));

        assertAll(() -> {
            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getPath()).isEqualTo("/user/create");
            assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
            assertThat(request.getParameter("id")).isEqualTo("1");
            assertThat(request.getParameter("name")).isEqualTo("bingbong");
            assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi");
            assertThat(request.getBodyParameter("password")).isEqualTo("password");
            assertThat(request.getBodyParameter("name")).isEqualTo("JaeSung");
        });
    }

    @DisplayName("HttpRequest TRACE 요청, 지원하지 않음")
    @Test
    void isMethodSupported_NotSupport() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_TRACE.txt"));

        assertThat(request.isMethodSupported()).isFalse();
    }

    @DisplayName("HttpRequest 쿠키 조회")
    @Test
    void getCookies() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_GET_SessionCookie_Login_True.txt"));

        assertThat(request.getCookies()).hasSize(1);
    }

    @DisplayName("HttpSession 조회 - JSESSIONID가 없는 경우, 세션 생성")
    @Test
    void getHttpSession_Not_Stored() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_GET_SessionCookie_Login_True.txt"));

        HttpSession httpSession = request.getSession();

        assertAll(() -> {
            assertThat(httpSession).isNotNull();
            assertThat(httpSession.getId()).isNotNull();
        });
    }

    @DisplayName("HttpSession 조회 - JSESSIONID가 있는 경우, 세션 조회")
    @Test
    void getHttpSession_Stored() throws IOException {
        HttpRequest request = new HttpRequest(
            createRequestBufferedReader("Http_Request_GET_Index_With_JSESSIONID.txt"));
        String randomUUID = "hello-world";
        HttpSessions.createHttpSession(randomUUID);

        HttpSession httpSession = request.getSession();

        assertAll(() -> {
            assertThat(httpSession).isNotNull();
            assertThat(httpSession.getId()).isEqualTo(randomUUID);
        });
    }
}
