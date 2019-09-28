package http.request;

import http.common.ContentType;
import http.common.HttpHeader;
import http.request.exception.InvalidHttpRequestException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestTest {
    private static final String CONTENT_TYPE = "Content-Type";

    @Test
    void 정상_생성() {
        RequestLine getRequestLine = new RequestLine("GET / HTTP/1.1");
        HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        RequestBody body = new RequestBody("", "");
        assertDoesNotThrow(() -> new HttpRequest(getRequestLine, httpHeader, body));
    }

    @Test
    void 생성_에러() {
        HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        RequestBody body = new RequestBody("", "");

        assertThrows(InvalidHttpRequestException.class, () ->
                new HttpRequest(null, httpHeader, body));
    }

    @Test
    void queryString_처리() {
        RequestLine getRequestLine = new RequestLine("GET /user?id=1 HTTP/1.1");
        HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        RequestBody body = new RequestBody("", "");

        HttpRequest httpRequest = new HttpRequest(getRequestLine, httpHeader, body);
        assertThat(httpRequest.getQueryParameter("id")).isEqualTo("1");
    }

    @Test
    void body로_전달된_request_parameter_처리() {
        RequestLine postRequestLine = new RequestLine("POST / HTTP/1.1");
        HttpHeader httpHeader = new HttpHeader(Arrays.asList(
                String.format("%s: %s", CONTENT_TYPE, ContentType.FORM_URLENCODED)));
        RequestBody body = new RequestBody("id=1", ContentType.FORM_URLENCODED);
        HttpRequest httpRequest = new HttpRequest(postRequestLine, httpHeader, body);

        assertThat(httpRequest.getFormDataParameter("id")).isEqualTo("1");
    }
}