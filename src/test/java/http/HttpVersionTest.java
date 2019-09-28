package http;

import http.exception.NotFoundVersionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpVersionTest {

    @Test
    void 버전_정상적으로_가져오기() {
        String httpVersion = "HTTP/1.1";

        assertEquals(HttpVersion.of(httpVersion), HttpVersion.V_1_1);
    }

    @Test
    void 존재하지_않는_버전_가져오기_오류() {
        String httpVersion = "HTTP/1.5";

        assertThrows(NotFoundVersionException.class, () -> HttpVersion.of(httpVersion));
    }
}
