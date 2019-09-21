package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {
    @Test
    public void toStringTest() {
        assertThat(HttpStatus.NOT_FOUND.toString()).isEqualTo("404 NOT FOUND");
    }
}