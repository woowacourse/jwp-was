package http.response;

import http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static http.HttpVersion.HTTP_1_1;
import static http.response.HttpResponse.CRLF;
import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    private static final String BODY = "body";

    private HttpStatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        statusLine = new HttpStatusLine(HTTP_1_1, OK);
        headers = new HttpHeaders();
        body = BODY.getBytes();
    }

    @Test
    void hasBody() {
        httpResponse = new HttpResponse(statusLine, headers, body);

        assertThat(httpResponse.hasBody()).isTrue();
    }

    @Test
    void HttpResponse메시지_생성() {
        httpResponse = new HttpResponse(statusLine, headers, body);
        headers.put("key", "value");

        String responseMessage = httpResponse.toString();

        assertThat(responseMessage).isEqualTo("HTTP/1.1 200 OK" + CRLF
                + "key: value" + CRLF
                + CRLF
                + BODY);
    }

    @Test
    void HttpREsponse_헤더_메시지_생성() {
        httpResponse = new HttpResponse(statusLine, headers, body);
        headers.put("key1", "value1");
        headers.put("key2", "value2");

        String headerMessge = httpResponse.getHeaderMessage();

        assertThat(headerMessge).isEqualTo("HTTP/1.1 200 OK" + CRLF
                + "key1: value1" + CRLF
                + "key2: value2" + CRLF
                + CRLF);
    }
}
