package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestUtilsTest {
    private RequestUtils requestUtils;

    @BeforeEach
    void setUp() {
        requestUtils = new RequestUtils();
    }

    @Test
    void extractFileName() {
        String fileName = requestUtils.extractFileName("GET /index.html HTTP/1.1");
        assertThat(fileName).isEqualTo("index.html");
    }
}
