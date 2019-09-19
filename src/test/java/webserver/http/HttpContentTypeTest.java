package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.parser.KeyValueParserFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpContentTypeTest {
    @Test
    @DisplayName("정상적인 HttpContentType 객체를 생성한다.")
    void httpContentType() {
        String input = "text/html; charset=utf-8";

        HttpContentType httpContentType = new HttpContentType(input, KeyValueParserFactory.getInstance());

        assertNotNull(httpContentType);
    }
}