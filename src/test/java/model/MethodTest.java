package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MethodTest {

    @ParameterizedTest
    @DisplayName("Method of")
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    void create(String method) {
        assertThat(Method.of(method)).isInstanceOf(Method.class);
    }
}
