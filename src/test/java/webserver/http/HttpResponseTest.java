package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.parser.KeyValueParserFactory;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    @Test
    @DisplayName("정상적인 HttpResponse 객체를 생성한다.")
    void httpResponse() {
        String input = "text/html; charset=utf-8";
        HttpContentType httpContentType = new HttpContentType(input, KeyValueParserFactory.getInstance());

        HttpResponse httpResponse = new HttpResponse.Builder()
                .httpStatusCode(HttpStatusCode.OK)
                .httpContentType(httpContentType)
                .body("Hello World".getBytes())
                .build();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHttpContentType()).isEqualTo(httpContentType);
        assertThat(httpResponse.getBody()).isEqualTo("Hello World".getBytes());
    }
}