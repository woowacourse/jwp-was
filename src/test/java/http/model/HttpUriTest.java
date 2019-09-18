package http.model;

import http.supoort.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class HttpUriTest {
    HttpUri httpUri;

    @Test
    void HTTP_URI_가_아닌경우() {
        assertThatThrownBy(()->new HttpUri("something")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void HTTP_URI_가_맞는경우() {
        assertDoesNotThrow(()->new HttpUri("/index.html"));
    }
}