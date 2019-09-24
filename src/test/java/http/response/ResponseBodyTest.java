package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {
    private ResponseBody responseBody;

    @BeforeEach
    public void setUp() {
        responseBody = new ResponseBody("test".getBytes());
    }

    @Test
    public void getBody() {
        assertThat(responseBody.getBody()).isEqualTo("test".getBytes());
    }
}