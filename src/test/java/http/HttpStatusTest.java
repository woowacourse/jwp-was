package http;

import http.exception.NotFoundStatusException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpStatusTest {
    @Test
    void OK_of_정상_동작() {
        assertThat(HttpStatus.of(200)).isEqualTo(HttpStatus.OK);
    }

    @Test
    void FOUND_of_정상_동작() {
        assertThat(HttpStatus.of(302)).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void getStatus_정상_동작() {
        assertThat(HttpStatus.OK.getStatus()).isEqualTo("OK");
    }

    @Test
    void of_없는_코드일_경우_에러() {
        assertThrows(NotFoundStatusException.class, () -> HttpStatus.of(-1));
    }
}