package webserver.http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class HttpStatusTest {
    @DisplayName("에러코드인지 확인한다.")
    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    void isErrorCode(HttpStatus httpStatus) {
        assertThat(httpStatus.isErrorCode()).isEqualTo(httpStatus.getStatusCode() >= 400);
    }
}
