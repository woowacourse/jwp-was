package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.headerfields.HttpContentType;
import webserver.http.headerfields.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpResponseTest {
    @Test
    @DisplayName("정상적인 HttpResponse 객체를 생성한다.")
    void httpResponse() {
        String input = "text/html; charset=utf-8";
        HttpContentType httpContentType = HttpContentType.of(input).get();

        assertNotNull(HttpResponse.builder(httpContentType)
                .statusCode(HttpStatusCode.OK)
                .body("Hello World")
                .location("/index.html")
                .build());
    }
}