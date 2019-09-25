package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseStatusTest {
    @Test
    void serialize_확인() {
        assertThat(ResponseStatus.OK.serialize()).isEqualTo("200 OK");
    }
}