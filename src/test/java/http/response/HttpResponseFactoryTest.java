package http.response;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpResponse.CRLF;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseFactoryTest {
    @Test
    void Http_200_Response_생성() {
        byte[] body = "body".getBytes();
        String contentsType = "text/html";
        HttpResponseBody responseBody = new HttpResponseBody(body, contentsType);
        HttpResponse httpResponse = HttpResponseFactory.makeHttp200Response(responseBody);

        String headerMessage = "HTTP/1.1 200 OK" + CRLF
                + "Content-Type: " + contentsType + CRLF
                + "Content-Length: " + body.length + CRLF
                + CRLF;

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headerMessage);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void Http_302_Response_생성() {
        String location = "http://localhost:8080/index.html";
        HttpResponse httpResponse = HttpResponseFactory.makeHttp302Response(location);

        String responseMessage = "HTTP/1.1 302 Found" + CRLF
                + "Location: " + location + CRLF
                + CRLF;

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(responseMessage);
    }

    @Test
    void Http_404_Response_생성() throws IOException, URISyntaxException {
        byte[] body = "404 Not Found".getBytes();
        String contentsType = "text/html";
        HttpResponseBody responseBody = new HttpResponseBody(body, contentsType);
        HttpResponse httpResponse = HttpResponseFactory.makeHttp404Response(responseBody);

        String headerMessage = "HTTP/1.1 404 Not Found" + CRLF
                + "Content-Type: text/html" + CRLF
                + "Content-Length: 13" + CRLF
                + CRLF;

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headerMessage);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}