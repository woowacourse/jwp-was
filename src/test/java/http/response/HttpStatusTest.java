package http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"OK,200", "FOUND,302"})
    void getCode(HttpStatus actual, int expected) {
        assertThat(actual.getCode()).isEqualTo(expected);
    }
}