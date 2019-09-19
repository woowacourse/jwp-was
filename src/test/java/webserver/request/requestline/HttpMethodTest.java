package webserver.request.requestline;

import exception.IllegalHttpMethodException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpMethodTest {

    @Test
    void of() {
        assertThat(HttpMethod.of("GET")).isEqualByComparingTo(HttpMethod.GET);
    }

    @Test
    void of_illegalHttpMethodException() {
        assertThrows(IllegalHttpMethodException.class, () -> HttpMethod.of("HTTP"));
    }
}