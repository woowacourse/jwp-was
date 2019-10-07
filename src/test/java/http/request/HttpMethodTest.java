package http.request;

import http.NotSupportedHttpMethodException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @Test
    void fromName_notExistMethodName() {
        String notExistMethodName = "NOT_EXIST";

        assertThrows(NotSupportedHttpMethodException.class, () -> HttpMethod.fromMethodName(notExistMethodName));
    }

    @Test
    void fromName_existMethodName() {
        String existMethodName = "GET";

        assertThat(HttpMethod.fromMethodName(existMethodName)).isEqualTo(HttpMethod.GET);
    }

    @Test
    void fromName_existMethodNameOfNotUpperCase() {
        String existMethodNameOfNotUpperCase = "Get";

        assertThat(HttpMethod.fromMethodName(existMethodNameOfNotUpperCase)).isEqualTo(HttpMethod.GET);
    }
}