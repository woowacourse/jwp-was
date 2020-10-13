package model.general;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StatusTest {

    @ParameterizedTest
    @DisplayName("Status 생성 확인")
    @CsvSource(value = {"OK", "FOUND", "METHOD_NOT_ALLOWED"})
    void getContentType(Status status) {
        assertThat(status).isInstanceOf(Status.class);
    }

    @ParameterizedTest
    @DisplayName("StatusCode 확인")
    @CsvSource(value = {
        "OK:200",
        "FOUND:302",
        "METHOD_NOT_ALLOWED:405"
    }, delimiter = ':')
    void getStatusCode(Status status, int statusCode) {
        assertThat(status.getStatusCode()).isEqualTo(statusCode);
    }

    @ParameterizedTest
    @DisplayName("reasonPhrase 확인")
    @CsvSource(value = {
        "OK:OK",
        "FOUND:Found",
        "METHOD_NOT_ALLOWED:Method Not Allowed"
    }, delimiter = ':')
    void getStatusName(Status status, String statusName) {
        assertThat(status.getReasonPhrase()).isEqualTo(statusName);
    }
}
