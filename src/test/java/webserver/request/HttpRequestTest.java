package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import request.Method;

class HttpRequestTest {

    private String requestHeader;
    private String requestBody;
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        requestBody = "id=3456789";
        httpRequest = new HttpRequest(requestHeader, requestBody);
    }

    @Test
    void isExistRequestHeader() {
        assertThat(HttpRequest.isExistRequestHeader(requestHeader, "Content-Length"))
            .isTrue();
        assertThat(HttpRequest.isExistRequestHeader(requestHeader, "X-Auth-Token"))
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
        assertThat(HttpRequest.findHeaderValue(test, "Content-Length"))
            .isEqualTo("250");
    }

    @Test
    void isUriUsingQueryString() {
        HttpRequest expectedTrue = new HttpRequest(
            "GET /join?id=1 HTTP/1.1\n", "");

        HttpRequest expectedFalse = new HttpRequest(
            "GET /join HTTP/1.1\n", "");

        assertThat(expectedTrue.isUriUsingQueryString()).isTrue();
        assertThat(expectedFalse.isUriUsingQueryString()).isFalse();
    }

    @Test
    void getQueryDataFromUri() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "1");

        assertThat(httpRequest.getQueryDataFromUri()).isEqualTo(expected);
    }

    @Test
    void getFormDataFromBody() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "3456789");

        assertThat(httpRequest.getFormDataFromBody()).isEqualTo(expected);
    }

    @Test
    void getFormDataFromBody_IfMessageBodyIsEmpty_ThrowException() {
        HttpRequest httpRequest = new HttpRequest(requestHeader, "");

        assertThatThrownBy(httpRequest::getFormDataFromBody)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("message body is empty.");
    }

    @Test
    void getFormDataFromBody_IfRequestHasNotFormData_ThrowException() {
        HttpRequest httpRequest = new HttpRequest(requestHeader, "01234");

        assertThatThrownBy(httpRequest::getFormDataFromBody)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("message body is not form data format.");
    }

    @Test
    void isUriPath() {
        assertThat(httpRequest.isUriPath("/join")).isTrue();
        assertThat(httpRequest.isUriPath("/join?")).isFalse();
    }

    @Test
    void isMethod() {
        assertThat(httpRequest.isMethod(Method.GET)).isTrue();
        assertThat(httpRequest.isMethod(Method.POST)).isFalse();
    }

    @Test
    void getMethod() {
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUriPath() {
        assertThat(httpRequest.getUriPath()).isEqualTo("/join");
    }

    @Test
    void getHeaderValue() {
        assertThat(httpRequest.getHeader("Host"))
            .isEqualTo("localhost:8080");
    }
}