package webserver.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.HttpCookie;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.MimeType;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;
import static webserver.controller.response.HttpResponse.STATIC_FILE_PATH;

public class HttpResponseTests {
    private static final String TEST_MESSAGE = "올바르지 않은 요청입니다.";
    HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = mock(HttpRequest.class);
        when(httpRequest.getVersion()).thenReturn("HTTP/1.1");
    }

    @Test
    void ok() {
        when(httpRequest.getMimeType()).thenReturn(MimeType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("." + NON_STATIC_FILE_PATH + "/index.html").get();
        HttpResponse httpResponse = HttpResponse.ok(httpRequest, body);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void staticFile_ok() {
        when(httpRequest.getMimeType()).thenReturn(MimeType.CSS);
        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_FILE_PATH + "/css/bootstrap.min.css").get();
        HttpResponse httpResponse = HttpResponse.ok(httpRequest, body);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void login_fail_redirect() {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.loginCookie(false, "/");

        String redirectUrl = "/user/login_failed.html";
        HttpResponse httpResponse = HttpResponse.sendRedirect(httpRequest, redirectUrl, false);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        assertThat(responseHeaderFields.get("Set-Cookie")).isEqualTo("logined=false; Path=/");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void non_login_redirect() {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.loginCookie(false, "/");

        String redirectUrl = "/index.html";
        HttpResponse httpResponse = HttpResponse.sendRedirect(httpRequest, redirectUrl, false);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        assertThat(responseHeaderFields.get("Set-Cookie")).isEqualTo("logined=false; Path=/");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void login_sucess_redirect() {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.loginCookie(true, "/");

        String redirectUrl = "/user/list";
        HttpResponse httpResponse = HttpResponse.sendRedirect(httpRequest, redirectUrl, true);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        assertThat(responseHeaderFields.get("Set-Cookie")).isEqualTo("logined=true; Path=/");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void badRequest() {
        HttpResponse httpResponse = HttpResponse.badRequest(TEST_MESSAGE);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        assertThat(responseHeaderFields.get("Connection")).isEqualTo("close");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseHeaderFields.get("message")).isEqualTo(TEST_MESSAGE);
    }
}
