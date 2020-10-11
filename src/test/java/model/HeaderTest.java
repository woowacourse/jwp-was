package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class HeaderTest {

    @ParameterizedTest
    @DisplayName("Header of")
    @ValueSource(strings = {"Content-Length", "content-length"})
    void create(String name) {
        Header header = Header.of(name);

        assertThat(header).isInstanceOf(Header.class);
    }

    @ParameterizedTest
    @DisplayName("Header of - 헤더 이름이 유효하지 않을 경우")
    @ValueSource(strings = {"Invalid-Content-Length", ""})
    void create_IfInvalidName_ThrowException(String name) {
        assertThatThrownBy(() -> Header.of(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unknown Header");
    }

}
