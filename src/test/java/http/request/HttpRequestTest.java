package http.request;

import http.request.support.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static testhelper.Common.getHttpRequest;

public class HttpRequestTest {

    @Test
    @DisplayName("HTTP GET 요청")
    public void httpGetRequest() throws IOException {
        HttpRequest httpRequest = getHttpRequest("HTTP_GET_QUERY_STRING.txt");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("HTTP POST 요청")
    public void httpPostRequest() throws IOException {
        HttpRequest httpRequest = getHttpRequest("HTTP_POST_USER_CREATE.txt");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("91");
        assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("HTTP Cookie Test")
    public void httpRequestCookie() throws IOException {
        HttpRequest httpRequest = getHttpRequest("HTTP_GET_USER_LIST_LOGIN.txt");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/list");
        assertThat(httpRequest.getCookie("Path")).isEqualTo("/");
    }
}