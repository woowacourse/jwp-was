package webserver.http.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpHeaderTest {

    @ParameterizedTest
    @CsvSource(value = {"Host|localhost:8080", "Connection|keep-alive", "Accept|*/*"}, delimiter = '|')
    void 생성(String key, String value) {
        HttpHeader httpHeader = HttpHeader.of(
                Arrays.asList(
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*"
                )
        );

        assertEquals(httpHeader.get(key), value);
    }

    @Test
    void Location_add_확인() {
        HttpHeader httpHeader = HttpHeader.of(Arrays.asList("Location: /index.html"));
        assertEquals("/index.html", httpHeader.get("Location"));
    }
}