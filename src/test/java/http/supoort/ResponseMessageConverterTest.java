package http.supoort;

import http.model.HttpResponse;
import http.model.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseMessageConverterTest {
    @Test
    void NOT_FOUND_convert_테스트() {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/404_ERROR.html")
                .status(HttpStatus.NOT_FOUND)
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 404 Not Found")).isTrue();
    }

    @Test
    void Redirect_convert_테스트() {
        HttpResponse httpResponse = HttpResponse.builder()
                .sendRedirect("/index.html")
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 302 Found")).isTrue();
    }

    @Test
    void Forward_convert_테스트() {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/index.html")
                .build();

        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 200 OK")).isTrue();
    }
}