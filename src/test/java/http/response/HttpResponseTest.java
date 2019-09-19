package http.response;

import org.junit.jupiter.api.Test;

import static http.response.HttpResponse.CRLF;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    @Test
    void Http_Response_생성() {
        HttpResponse httpResponse = new HttpResponse();
        byte[] body = "body".getBytes();

        httpResponse.setHeader("key", "value");
        httpResponse.setBody(body, "text/html;charset=utf-8");

        String headers = "HTTP/1.1 200 OK" + CRLF
                + "key: value" + CRLF
                + "Content-Type: text/html;charset=utf-8" + CRLF
                + "Content-Length: " + body.length + CRLF
                + CRLF;

        assertThat(httpResponse.getHeaders()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}