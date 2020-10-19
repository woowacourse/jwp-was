package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpResponseTest {

    private ByteArrayOutputStream byteArrayOutputStream;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        response = new HttpResponse(byteArrayOutputStream);
    }

    @DisplayName("HttpResponse Forward - HTML")
    @Test
    public void responseForward_HTML() throws Exception {
        response.forward("/index.html");
        String body = makeBody(byteArrayOutputStream);

        assertAll(() -> {
            assertThat(body).contains("HTTP/1.1 200 Ok");
            assertThat(body).contains("Content-Length: ");
        });
    }

    @DisplayName("HttpResponse Forward - CSS")
    @Test
    public void responseForward_CSS() throws Exception {
        response.forward("/css/styles.css");
        String body = makeBody(byteArrayOutputStream);

        assertThat(body).contains("HTTP/1.1 200 Ok");
    }

    @DisplayName("HttpResponse Redirect")
    @Test
    public void responseRedirect() throws Exception {
        response.sendRedirect("/index.html");
        String body = makeBody(byteArrayOutputStream);

        assertAll(() -> {
            assertThat(body).contains("HTTP/1.1 302 Found");
            assertThat(body).contains("Location: /index.html");
        });
    }

    @DisplayName("HttpResponse MethodNotAllowed")
    @Test
    public void responseMethodNotAllowed() throws Exception {
        response.methodNotAllowed("/error.html");
        String body = makeBody(byteArrayOutputStream);

        assertThat(body).contains("HTTP/1.1 405 Method Not Allowed");
    }

    @DisplayName("HttpResponse NotImplemented")
    @Test
    public void responseNotImplemented() throws Exception {
        response.notImplemented("/error.html");
        String body = makeBody(byteArrayOutputStream);

        assertThat(body).contains("HTTP/1.1 501 Not Implemented");
    }

    @DisplayName("Cookie 추가")
    @Test
    public void addCookie() {
        response.addCookie(new Cookie("name", "bingbong"));

        List<Cookie> cookies = response.getCookies();
        assertAll(() -> {
            assertThat(cookies.get(0).getName()).isEqualTo("name");
            assertThat(cookies.get(0).getValue()).isEqualTo("bingbong");
        });
    }

    @DisplayName("Cookies 조회")
    @Test
    public void getCookies() {
        response.addCookie(new Cookie("name", "bingbong"));
        response.addCookie(new Cookie("generation", "2"));

        List<Cookie> cookies = response.getCookies();
        assertThat(cookies).hasSize(2);
    }

    private String makeBody(ByteArrayOutputStream byteArrayOutputStream) {
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }
}
