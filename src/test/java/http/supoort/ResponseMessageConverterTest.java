package http.supoort;

import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseMessageConverterTest {
    @Test
    void NOT_FOUND_CONVERT_테스트() {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/404_ERROR.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.NOT_FOUND)
                .build();

        System.out.println(ResponseMessageConverter.convertHeader(httpResponse));
        assertThat(ResponseMessageConverter.convertHeader(httpResponse).contains("HTTP/1.1 404 Not Found")).isTrue();
    }
}