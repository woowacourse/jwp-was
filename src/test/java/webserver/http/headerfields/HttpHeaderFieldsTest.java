package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderFieldsTest {
    private static final Map<String, String> DEFAULT_HEADER_FIELDS = new HashMap<>();
    static {
        DEFAULT_HEADER_FIELDS.put("key1", "value1");
    }

    @Test
    @DisplayName("정상적으로 HTTP header fields를 생성한다.")
    void initHeaderFields() {
        assertNotNull(HttpHeaderFields.init(DEFAULT_HEADER_FIELDS));
    }

    @Test
    @DisplayName("헤더 필드에 있는 값을 가져온다.")
    void getHeaderFieldValue() {
        HttpHeaderFields httpHeaderFields = HttpHeaderFields.init(DEFAULT_HEADER_FIELDS);

        assertThat(httpHeaderFields.value("key1")).isEqualTo("value1");
    }

    @Test
    @DisplayName("헤더 필드에 없는 값을 가져오는 경우 없는 값을 가져온다.")
    void getHeaderFieldValueFail() {
        HttpHeaderFields httpHeaderFields = HttpHeaderFields.init(DEFAULT_HEADER_FIELDS);

        assertNull(httpHeaderFields.value("key0"));
    }

    @Test
    @DisplayName("HTTP header field를 디버그용 String으로 파싱한다.")
    void debugStringField() {
        HttpHeaderFields httpHeaderFields = HttpHeaderFields.init(DEFAULT_HEADER_FIELDS);

        assertTrue(httpHeaderFields.debugString().length() > 0);
    }
}