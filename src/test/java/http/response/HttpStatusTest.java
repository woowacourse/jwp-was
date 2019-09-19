package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {
    @Test
    void status_메시지_생성() {
        String status200Message = "200 OK";

        assertThat(HttpStatus.OK.getMessage()).isEqualTo(status200Message);
    }
}