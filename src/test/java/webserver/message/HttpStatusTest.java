package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    @DisplayName("HttpStatus enum 객체 toString 확인")
    void toString(HttpStatus status) {
        assertThat(status.toString()).isEqualTo(status.getCode() + " " + status.getPhrase());
    }

}