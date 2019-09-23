package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpStatusTest {

    @Test
    void getter_테스트() {
        assertThat(HttpStatus.OK.getCode()).isEqualTo(200);
        assertThat(HttpStatus.OK.getPhrase()).isEqualTo("OK");
    }

    @Test
    void from_fullStatus_넣었을때() {
        assertThat(HttpStatus.from("201 Created")).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void from_code만_넣었을때() {
        assertThat(HttpStatus.from("201")).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void from_phrase만_넣었을때() {
        assertThat(HttpStatus.from("Created")).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void from_없는_status_넣었을때_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> HttpStatus.from("REMOVE"));
    }
}
