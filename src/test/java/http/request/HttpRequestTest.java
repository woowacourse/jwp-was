package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET", "/user/create", "HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("Accept", "text/html");
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net");

        httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    @Test
    void getUri() {
        assertThat(httpRequest.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getHeaderByName() {
        assertThat(httpRequest.getHeaderByName("Accept")).isEqualTo("text/html");
    }

    @Test
    void getBodyByName() {
        assertThat(httpRequest.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequest.getHttpRequestBodyByName("name")).isEqualTo("자바지기");
        assertThat(httpRequest.getHttpRequestBodyByName("email")).isEqualTo("javajigi@slipp.net");
    }
}