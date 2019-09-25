package webserver.http.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.common.exception.InvalidHeaderLines;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {

    @ParameterizedTest
    @CsvSource(value = {"Host|localhost:8080", "Connection|keep-alive", "Accept|*/*"}, delimiter = '|')
    void 생성(String key, String value) {
        HttpHeader httpHeader = new HttpHeader(Arrays.asList(key + ": " + value));

        assertEquals(httpHeader.get(key), value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Host localhost:8080", "Connectoin keep-alive", "Accept */*"})
    void 형식에_맞지_않는_헤더_라인(String value) {
        HttpHeader httpHeader = new HttpHeader(Arrays.asList(value));

        assertFalse(httpHeader.getHeaders().containsKey(value.split(" ")[0]));
    }

    @Test
    void 헤더에_빈_칸이_들어가는_경우() {
        HttpHeader httpHeader = new HttpHeader(Arrays.asList(""));

        assertFalse(httpHeader.getHeaders().containsKey(""));
    }

    @Test
    void Location_add_확인() {
        HttpHeader httpHeader = new HttpHeader(Arrays.asList("Location: /index.html"));
        assertEquals("/index.html", httpHeader.get("Location"));
    }

    @Test
    void 헤더에_null이_들어가는_경우() {
        assertThrows(InvalidHeaderLines.class, () -> {
            new HttpHeader(null);
        });
    }

    @Test
    void 헤더_사이즈가_0인_경우() {
        assertEquals(new HttpHeader(Arrays.asList()).getHeaders(), Collections.emptyMap());
    }
}