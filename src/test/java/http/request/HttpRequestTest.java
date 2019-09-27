package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testhelper.Common;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    @DisplayName("HTTP GET 요청")
    public void httpGetRequest() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfText("HTTP_GET_QUERY_STRING.txt"));

        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("HTTP POST 요청")
    public void httpPostRequest() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt"));

        assertThat(httpRequest.getMethod()).isEqualTo("POST");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("91");
        assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

//    @Test
//    @DisplayName("HTTP Cookie Test")
//    public void httpRequestCookie() throws IOException {
//        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfText("HTTP_GET_USER_LIST.txt"));
//
//        assertThat(httpRequest.getMethod()).isEqualTo("GET");
//        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/list");
//        assertThat(httpRequest.getCookie("logined")).isEqualTo("true");
//        assertThat(httpRequest.getCookie("Path")).isEqualTo("/");
//    }
}