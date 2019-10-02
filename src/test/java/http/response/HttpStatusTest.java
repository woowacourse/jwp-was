package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {
    @Test
    @DisplayName("status message 생성")
    void create() {
        String status200Message = "200 OK";

        assertThat(HttpStatus.OK.getMessage()).isEqualTo(status200Message);
    }

    @Test
    @DisplayName("stauts 매치")
    void match() {
        HttpStatus httpStatus = HttpStatus.OK;

        assertThat(HttpStatus.OK.match(httpStatus)).isTrue();
    }
}