package http.response;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseBodyTest {
    @Test
    void 길이가_0인_body_확인() {
        assertEquals(0, HttpResponseBody.empty().getLength());
    }

    @Test
    void 길이가_0이_아닌_body_확인() {
        byte[] body = new byte[]{1, 2, 3};
        assertEquals(3, HttpResponseBody.of(body).getLength());
    }
}