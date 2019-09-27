package servlet.request;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testhelper.Common;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServletRequestTest {

    @Test
    @DisplayName("HttpServletRequest 동작 테스트")
    public void httpServletRequestCookie() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_GET_USER_LIST_LOGIN.txt"));
        HttpServletRequest httpServletRequest = new HttpServletRequest(httpRequest);

        assertThat(httpServletRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpServletRequest.getCookie("logined")).isEqualTo("true");
        assertThat(httpServletRequest.getCookie("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("HttpServletRequest 동작 테스트")
    public void httpServletRequestParameter() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt"));
        HttpServletRequest httpServletRequest = new HttpServletRequest(httpRequest);

        assertThat(httpServletRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpServletRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpServletRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }
}