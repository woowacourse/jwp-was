package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpMethodTest {

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class,
            names = {"GET", "POST", "PUT", "DELETE"})
    @DisplayName("HttpMethod enum 객체에서 Http Method 이름을 제대로 불러오는지 확인")
    void create(HttpMethod method) {
        assertNotNull(method);
    }
}