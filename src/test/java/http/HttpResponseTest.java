package http;

import http.exception.EmptyStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpResponseTest {
    private DataOutputStream dos;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        dos = new DataOutputStream(new ByteArrayOutputStream());
        httpResponse = new HttpResponse(dos);
    }

    @Test
    void 응답_메시지_정상_전송() {
        httpResponse.setStatus(200);
        assertDoesNotThrow(httpResponse::send);
    }

    @Test
    void 비어있는_상태_코드_응답_메시지_오류() {
        assertThrows(EmptyStatusException.class, httpResponse::send);
    }
}
