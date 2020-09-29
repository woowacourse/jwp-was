package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private HttpRequest httpRequestGetMethod;
    private HttpRequest httpRequestPostMethod;

    @BeforeEach
    void setUp() {
        HttpRequestLine httpRequestLine1 = new HttpRequestLine("GET", new HttpRequestUrl("/user/create?userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net"), "HTTP/1.1");
        HttpRequestLine httpRequestLine2 = new HttpRequestLine("POST", new HttpRequestUrl("/user/create"), "HTTP/1.1");

        final Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Host", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(requestHeader);

        HttpRequestBody httpRequestBody1 = HttpRequestBody.emptyBody();
        HttpRequestBody httpRequestBody2 = new HttpRequestBody("userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net");

        httpRequestGetMethod = new HttpRequest(httpRequestLine1, httpRequestHeader, httpRequestBody1);
        httpRequestPostMethod = new HttpRequest(httpRequestLine2, httpRequestHeader, httpRequestBody2);
    }

    @Test
    void getUri() {
        assertThat(httpRequestGetMethod.getUrl()).isEqualTo("/user/create");
        assertThat(httpRequestPostMethod.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getHttpRequestHeaderByName() {
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void getHttpRequestParamsByName() {
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("password")).isEqualTo("password");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("name")).isEqualTo("자바지기");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void getHttpRequestBodyByName() {
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("name")).isEqualTo("자바지기");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("email")).isEqualTo("javajigi@slipp.net");
    }
}