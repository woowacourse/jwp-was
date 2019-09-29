package http.supoort;

import http.model.ContentType;
import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseMessageConverterTest {
    @Test
    void NOT_FOUND_convert_테스트() {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/404_ERROR.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.NOT_FOUND)
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 404 Not Found")).isTrue();
    }

    @Test
    void Redirect_convert_테스트() {
        HttpResponse httpResponse = HttpResponse.builder()
                .sendRedirect("/index.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.FOUND)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 302 Found")).isTrue();
    }

    @Test
    void Forward_convert_테스트() {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/index.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.OK)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 200 OK")).isTrue();
    }
}