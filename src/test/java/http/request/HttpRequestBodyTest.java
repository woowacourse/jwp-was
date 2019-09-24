package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestBodyTest {

    private static final String TEST_STRING = "hello world!";

    @Test
    void 생성() {
        assertThat(new HttpRequestBody(TEST_STRING).getBody()).isEqualTo(TEST_STRING);
    }
}
