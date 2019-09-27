package http.request;

import http.common.HttpVersion;
import http.parser.HttpUriParser;
import http.parser.RequestHeaderParser;
import http.parser.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static http.request.HttpRequest.HttpRequestBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    private HttpRequest httpRequest = HttpRequestBuilder.builder()
        .withRequestLine(RequestLineParser.parse("GET /user/create?id=conas&password=1234 HTTP/1.1"))
        .withRequestHeader(RequestHeaderParser.parse(Arrays.asList("Host: localhost:8080", "Connection: keep-alive")))
        .withRequestBody(new RequestBody(new HashMap<String, String>() {{
            put("name", "conatuseus");
            put("email", "conatuseus@gmail.com");
        }}))
        .build();

    @Test
    void getHttpVersion() {
        assertThat(httpRequest.getHttpVersion()).isEqualByComparingTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void findHeader() {
        assertThat(httpRequest.findHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.findHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void getUri() {
        assertThat(httpRequest.getUri()).isEqualTo(HttpUriParser.parse("/user/create?id=conas&password=1234"));
    }

    @Test
    void getPath() {
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }

    @Test
    void getQuery() {
        assertThat(httpRequest.getQuery()).isEqualTo("id=conas&password=1234");
    }

    @Test
    void findUriParam() {
        assertThat(httpRequest.findUriParam("id")).isEqualTo("conas");
        assertThat(httpRequest.findUriParam("password")).isEqualTo("1234");
    }

    @Test
    void findBodyParam() {
        assertThat(httpRequest.findBodyParam("name")).isEqualTo("conatuseus");
        assertThat(httpRequest.findBodyParam("email")).isEqualTo("conatuseus@gmail.com");
    }

    @Test
    void getHttpMethod() {
        assertThat(httpRequest.getHttpMethod()).isEqualByComparingTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("static인지 templates인지 확인. uri에 확장자가 없으므로 default인 static을 예")
    void findPathPrefix() {
        assertThat(httpRequest.findPathPrefix()).isEqualTo("./static");
    }

    @Test
    void getClassPath() {
        assertThat(httpRequest.getClassPath()).isEqualTo("./static/user/create");
    }
}