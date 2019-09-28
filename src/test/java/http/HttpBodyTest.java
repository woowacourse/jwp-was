package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HttpBodyTest {

    @Test
    void HTTP_본문_정상_생성() {
        String body = "HTTP body";
        assertDoesNotThrow(() -> new HttpBody(body));
    }
}
