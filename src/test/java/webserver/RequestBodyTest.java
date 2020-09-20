package webserver;

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.InvalidRequestBodyException;

class RequestBodyTest {
    private static final String VALID_BODY = "userId=javajigi"
        + "&password=password"
        + "&name=pobi"
        + "&email=javajigi@slipp.net";
    private static final String INVALID_BODY = "userId";

    @DisplayName("정상적으로 Request body를 생성한다.")
    @Test
    void of() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "pobi");
        expected.put("email", "javajigi@slipp.net");

        RequestBody body = RequestBody.of(VALID_BODY);
        assertThat(body.getAttributes()).isEqualTo(expected);
    }

    @DisplayName("Body가 null인 경우 빈 body를 생성한다")
    @Test
    void noBody() {
        Map<String, String> expected = new LinkedHashMap<>();
        RequestBody nullBody = RequestBody.of(null);
        RequestBody emptyBody = RequestBody.of("");

        assertThat(nullBody.getAttributes()).isEqualTo(expected);
        assertThat(emptyBody.getAttributes()).isEqualTo(expected);
    }

    @DisplayName("Body가 value가 없는 경우 예외를 반환한다.")
    @Test
    void noValueBody() {
        assertThatThrownBy(() -> RequestBody.of(INVALID_BODY))
            .isInstanceOf(InvalidRequestBodyException.class)
            .hasMessage("지원하지 않는 request body 형식입니다.");
    }
}
