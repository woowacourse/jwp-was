package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestBodyTest {
    @DisplayName("from: 요청 body를 받아 인스턴스를 생성한다.")
    @Test
    void from() {
        // given
        String body = "userId=test@test.com&password=1q2w3e&name=hello&email=nullable@kakao.com";

        // when
        RequestBody requestBody = RequestBody.from(body);

        // then
        assertAll(
                () -> assertThat(requestBody.getValue("userId")).isEqualTo("test@test.com"),
                () -> assertThat(requestBody.getValue("password")).isEqualTo("1q2w3e"),
                () -> assertThat(requestBody.getValue("name")).isEqualTo("hello"),
                () -> assertThat(requestBody.getValue("email")).isEqualTo("nullable@kakao.com")
        );
    }
}
