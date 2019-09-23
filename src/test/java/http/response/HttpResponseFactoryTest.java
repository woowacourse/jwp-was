package http.response;

import org.junit.jupiter.api.Test;

import static http.HttpMediaType.DEFAULT_MEDIA_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseFactoryTest {
    @Test
    void Http200Response_메세지_생성() {
        HttpResponseEntity responseEntity = HttpResponseEntity.get200Response("");
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        byte[] body = "body".getBytes();
        httpResponse.setBody(body, DEFAULT_MEDIA_TYPE);

        String headers = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + DEFAULT_MEDIA_TYPE + "\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void Http302Response_메세지_생성() {
        String location = "/test.html";
        HttpResponseEntity responseEntity = HttpResponseEntity.get302Response(location);
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        String headers = "HTTP/1.1 302 Found\r\n"
                + "Location: " + location + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(null);
    }

    @Test
    void Http404Response_메세지_생성() {
        HttpResponseEntity responseEntity = HttpResponseEntity.get404Response();
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        byte[] body = "404 Not Found".getBytes();
        httpResponse.setBody(body, DEFAULT_MEDIA_TYPE);

        String headers = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: " + DEFAULT_MEDIA_TYPE + "\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";

        assertThat(httpResponse.getHeaderMessage()).isEqualTo(headers);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}