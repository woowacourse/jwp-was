package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.headerfields.HttpContentType;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpContentTypeTest {
    @Test
    @DisplayName("정상적인 HttpContentType 객체를 생성한다.")
    void httpContentType() {
        String input = "text/html; charset=utf-8";

        HttpContentType httpContentType = HttpContentType.of(input).get();

        assertNotNull(httpContentType);
    }
}