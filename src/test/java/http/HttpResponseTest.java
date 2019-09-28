package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {

    @Test
    void OK_메시지_응답() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.response2xx("".getBytes(), MimeType.of("/script.js"));
        assertEquals(httpResponse.getStatus(), HttpStatus.OK);
        assertEquals(httpResponse.getStatusLine(), "HTTP/1.1 200 OK\r\n");
    }

    @Test
    void FOUND_메시지_응답() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.response3xx("index.html");
        assertEquals(httpResponse.getStatus(), HttpStatus.FOUND);
        assertEquals(httpResponse.getStatusLine(), "HTTP/1.1 302 FOUND\r\n");
    }
}
