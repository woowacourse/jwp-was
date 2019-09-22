package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    @DisplayName("HttpMethod enum 객체에서 Http Method 이름을 제대로 불러오는지 확인")
    void create1(String method) {
        assertThat(HttpMethod.valueOf(method).name()).isEqualTo(method);
    }
}