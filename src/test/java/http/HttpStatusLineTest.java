package http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpStatusLineTest {
    @Test
    @DisplayName("버전-응답코드-응답내용 순으로 반환")
    void getLineTest() {
        HttpStatusLine httpStatusLine = HttpStatusLine.of(HttpStatus.OK, "HTTP/1.1");
        String expected = "HTTP/1.1 200 OK";
        assertEquals(expected, httpStatusLine.getLine());
    }

}
