package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ResponseBodyTest {

    private byte[] body = {'a', 'b', 'c'};

    @Test
    void create() {
        assertDoesNotThrow(() -> new ResponseBody(body));
    }

    @Test
    void getLength() {
        assertThat(new ResponseBody(body).getLength()).isEqualTo(3);
    }

    @Test
    void getBody() {
        assertThat(new ResponseBody(body).getBody()).isEqualTo(body);
    }
}