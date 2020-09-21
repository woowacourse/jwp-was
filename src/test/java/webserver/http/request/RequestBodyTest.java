package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {
    @DisplayName("생성 테스트")
    @Test
    void isEmpty() {
        assertThat(RequestBody.from("a=1&b=2")).isNotNull();
    }

    @DisplayName("값을 정확히 가지고 올 수 있다.")
    @Test
    void get() {
        RequestBody requestBody = RequestBody.from("a=1&b=2");

        assertAll(
            () -> assertThat(requestBody.getParameter("a")).isEqualTo("1"),
            () -> assertThat(requestBody.getBody()).isEqualTo("a=1&b=2")
        );
    }
}