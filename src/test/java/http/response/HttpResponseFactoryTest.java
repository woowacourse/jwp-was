package http.response;

import http.response.response_entity.Http200ResponseEntity;
import http.response.response_entity.Http302ResponseEntity;
import http.response.response_entity.Http404ResponseEntity;
import http.response.response_entity.HttpResponseEntity;
import org.junit.jupiter.api.Test;

import static http.HttpMediaType.DEFAULT_MEDIA_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseFactoryTest {
    @Test
    void Http200Response_생성() {
        HttpResponseEntity responseEntity = new Http200ResponseEntity("");
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        byte[] body = "body".getBytes();
        httpResponse.setBody(body, DEFAULT_MEDIA_TYPE);

        String headers = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: html/text\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void Http302Response_생성() {
        String location = "/test.html";
        HttpResponseEntity responseEntity = new Http302ResponseEntity(location);
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        String headers = "HTTP/1.1 302 Found\r\n"
                + "Location: " + location + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(null);
    }

    @Test
    void Http404Response_생성() {
        HttpResponseEntity responseEntity = new Http404ResponseEntity();
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        byte[] body = "404 Not Found".getBytes();
        httpResponse.setBody(body, DEFAULT_MEDIA_TYPE);

        String headers = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: html/text\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}