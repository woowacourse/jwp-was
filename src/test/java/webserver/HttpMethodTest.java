package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpMethod;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HttpMethodTest {

    @DisplayName("인자로 넘긴 methodName에 맞는 HttpMethod를 리턴")
    @Test
    void from() {
        HttpMethod post = HttpMethod.from("POST");

        assertThat(post).isEqualTo(HttpMethod.POST);
    }

    @DisplayName("존재하지 않는 methodName을 넘긴 경우 IllegalArgumentException 발생")
    @Test
    void from2() {
        assertThatThrownBy(() -> HttpMethod.from("HOST"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("허용되지 않은 HttpMethod:");
    }
}