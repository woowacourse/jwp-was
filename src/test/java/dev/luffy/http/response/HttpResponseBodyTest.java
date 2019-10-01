package dev.luffy.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseBodyTest {

    @DisplayName("HttpResponseBody 가 잘 생성된다.")
    @Test
    void name() {
        byte[] body = new byte[3];
        HttpResponseBody httpResponseBody = new HttpResponseBody(body);
        assertEquals(httpResponseBody.getBody().length, 3);
    }
}
