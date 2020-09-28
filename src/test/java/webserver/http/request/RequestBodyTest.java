package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        assertThat(RequestBody.from("a=1&b=2")).isNotNull();
    }

    @DisplayName("쿼리 형태의 바디가 올 경우도 생성된다.")
    @Test
    void fromWithNonQuery() {
        assertThat(RequestBody.from("hello")).isNotNull();
    }

    @DisplayName("값을 정확히 가지고 올 수 있다.")
    @Test
    void get() {
        RequestBody requestBody = RequestBody.from("a=1&b=2");

        assertAll(
            () -> assertThat(requestBody.getParameters("a")).contains("1"),
            () -> assertThat(requestBody.getBody()).isEqualTo("a=1&b=2")
        );
    }
}