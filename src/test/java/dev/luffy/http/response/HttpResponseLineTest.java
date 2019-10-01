package dev.luffy.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.luffy.http.HttpProtocol;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseLineTest {

    @DisplayName("HttpResponseLine 의 combine 메서드가 잘 동작한다.")
    @Test
    void combine() {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpProtocol.HTTP_1_1, HttpStatus.OK);
        assertEquals(httpResponseLine.combine(), "HTTP/1.1 200 OK \r\n");
    }
}
