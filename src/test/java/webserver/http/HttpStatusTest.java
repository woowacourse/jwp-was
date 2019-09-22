package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpStatusTest {

    @Test
    void getter_테스트() {
        assertThat(HttpStatus.OK.getCode()).isEqualTo(200);
        assertThat(HttpStatus.OK.getPhrase()).isEqualTo("OK");
    }
}
