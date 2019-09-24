package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {

    @Test
    void find_code_and_status() {
        assertThat(HttpStatus.OK.getCodeAndStatus()).isEqualTo("200 OK");
    }
}