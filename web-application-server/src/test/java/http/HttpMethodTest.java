package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HttpMethodTest {
    @DisplayName("HTTP Method 생성")
    @ParameterizedTest
    @CsvSource(value = {"get:GET", "gEt:GET", "geT:GET", "GEt:GET", "gET:GET", "GET:GET"}, delimiterString = ":")
    void createGET(String value, HttpMethod method) {
        HttpMethod created = HttpMethod.from(value);
        assertThat(created).isEqualTo(method);
    }

    @DisplayName("HTTP Method 생성 예외")
    @Test
    void createException() {
        assertThatThrownBy(() -> HttpMethod.from("HELLO WORLD"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}