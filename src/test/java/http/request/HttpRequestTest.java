package http.request;

import http.common.HttpHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static http.request.HttpRequest.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestTest {
    private RequestLine getRequestLine;
    private RequestLine postRequestLine;
    private HttpHeader httpHeader;
    private String body;

    @BeforeEach
    void setUp() {
        getRequestLine = new RequestLine("GET / HTTP/1.1");
        postRequestLine = new RequestLine("POST / HTTP/1.1");
        httpHeader = new HttpHeader(new ArrayList<>());
        body = "";
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
        RequestParameter requestParameter = new RequestParameter(queryString);
        assertThat(httpRequest.getRequestParameter()).isEqualTo(requestParameter);
    }

    @Test
    void body로_전달된_request_parameter_처리() {
        httpHeader = new HttpHeader(Arrays.asList(
                String.format("%s: application/x-www-form-urlencoded", CONTENT_TYPE)));
        body = "id=1";
        HttpRequest httpRequest = new HttpRequest(postRequestLine, httpHeader, body);
        RequestParameter requestParameter = new RequestParameter(body);
        assertThat(httpRequest.getRequestParameter()).isEqualTo(requestParameter);
    }
}