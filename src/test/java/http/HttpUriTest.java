package http;

import http.exception.EmptyUriException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpUriTest {

    @Test
    void 정적_파일_uri_정상_생성() {
        assertDoesNotThrow(() -> new HttpUri("/index.html"));
    }

    @Test
    void 비어있는_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpUri(""));
    }

    @Test
    void NULL_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpUri(null));
    }
}
