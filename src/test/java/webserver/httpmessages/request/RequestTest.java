package webserver.httpmessages.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestTest {

    private String requestHeader;
    private String requestBody;
    private Request request;

    @BeforeEach
    void setUp() {
        requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        requestBody = "id=3456789";
        request = new Request(requestHeader, requestBody);
    }

    @Test
    void isExistRequestHeader() {
        assertThat(Request.isExistRequestHeader(requestHeader, "Content-Length"))
            .isTrue();
        assertThat(Request.isExistRequestHeader(requestHeader, "X-Auth-Token"))
            .isFalse();
    }

    @Test
    void findHeaderValue() {
        String test = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 250\n";
        assertThat(Request.findHeaderValue(test, "Content-Length"))
            .isEqualTo("250");
    }

    @Test
    void isUriUsingQueryString() {
        Request expectedTrue = new Request(
            "GET /join?id=1 HTTP/1.1\n", "");

        Request expectedFalse = new Request(
            "GET /join HTTP/1.1\n", "");

        assertThat(expectedTrue.isUriUsingQueryString()).isTrue();
        assertThat(expectedFalse.isUriUsingQueryString()).isFalse();
    }

    @Test
    void getQueryDataFromUri() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "1");

        assertThat(request.getQueryDataFromUri()).isEqualTo(expected);
    }

    @Test
    void getFormDataFromBody() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "3456789");

        assertThat(request.getFormDataFromBody()).isEqualTo(expected);
    }

    @Test
    void isUriPath() {
        assertThat(request.isUriPath("/join")).isTrue();
        assertThat(request.isUriPath("/join?")).isFalse();
    }

    @Test
    void isMethod() {
        assertThat(request.isMethod(Method.GET)).isTrue();
        assertThat(request.isMethod(Method.POST)).isFalse();
    }

    @Test
    void getMethod() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUri() {
        assertThat(request.getUri()).isEqualTo("/");
    }

    @Test
    void getHeaderValue() {
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
    }
}