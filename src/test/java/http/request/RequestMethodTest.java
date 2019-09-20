package http.request;

import http.request.exception.InvalidRequestMethodException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestMethodTest {
    @Test
    void 메서드명으로_RequestMethod_찾기() {
        assertThrows(InvalidRequestMethodException.class, () -> RequestMethod.of("INVALID"));
    }

    @Test
    void hasBodyTest() {
        assertFalse(RequestMethod.GET.hasBody());
        assertFalse(RequestMethod.DELETE.hasBody());
        assertTrue(RequestMethod.POST.hasBody());
        assertTrue(RequestMethod.PUT.hasBody());
    }
}