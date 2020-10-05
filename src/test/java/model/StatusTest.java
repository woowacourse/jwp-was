package model;

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
    @DisplayName("StatusName 확인")
    @CsvSource(value = {
        "OK:OK",
        "FOUND:FOUND",
        "METHOD_NOT_ALLOWED:METHOD_NOT_ALLOWED"
    }, delimiter = ':')
    void getStatusName(Status status, String statusName) {
        assertThat(status.getStatusName()).isEqualTo(statusName);
    }

    @ParameterizedTest
    @DisplayName("Location 필요 여부 확인")
    @CsvSource(value = {
        "OK:false",
        "FOUND:true",
        "METHOD_NOT_ALLOWED:false"
    }, delimiter = ':')
    void isNeedLocation(Status status, boolean needLocation) {
        assertThat(status.isNeedLocation()).isEqualTo(needLocation);
    }

    @ParameterizedTest
    @DisplayName("Body 필요 여부 확인")
    @CsvSource(value = {
        "OK:true",
        "FOUND:false",
        "METHOD_NOT_ALLOWED:false"
    }, delimiter = ':')
    void isNeedBody(Status status, boolean needBody) {
        assertThat(status.isNeedBody()).isEqualTo(needBody);
    }
}
