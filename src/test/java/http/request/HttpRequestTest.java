package http.request;

import http.common.ContentType;
import http.common.HttpHeader;
import http.request.exception.InvalidHttpRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestTest {
    private static final String CONTENT_TYPE = "Content-Type";
    private RequestLine getRequestLine;
    private RequestLine postRequestLine;
    private HttpHeader httpHeader;
    private RequestBody body;

    @BeforeEach
    void setUp() {
        getRequestLine = new RequestLine("GET / HTTP/1.1");
        postRequestLine = new RequestLine("POST / HTTP/1.1");
        httpHeader = new HttpHeader(new ArrayList<>());
        body = new RequestBody("", "");
    }

    @Test
    void 정상_생성() {
        assertDoesNotThrow(() -> new HttpRequest(getRequestLine, httpHeader, body));
    }

    @Test
    void 생성_에러() {
        assertThrows(InvalidHttpRequestException.class, () ->
                new HttpRequest(null, httpHeader, body));
    }

    @Test
    void queryString_처리() {
        String queryString = "id=1";
        getRequestLine = new RequestLine(String.format("GET /user?%s HTTP/1.1", queryString));
        HttpRequest httpRequest = new HttpRequest(getRequestLine, httpHeader, body);
        assertThat(httpRequest.getQueryParameter("id")).isEqualTo("1");
    }

    @Test
    void body로_전달된_request_parameter_처리() {
        httpHeader = new HttpHeader(Arrays.asList(
                String.format("%s: %s", CONTENT_TYPE, ContentType.FORM_URLENCODED)));
        body = new RequestBody("id=1", ContentType.FORM_URLENCODED);
        HttpRequest httpRequest = new HttpRequest(postRequestLine, httpHeader, body);
        assertThat(httpRequest.getFormDataParameter("id")).isEqualTo("1");
    }
}