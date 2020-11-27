package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class URITest {
    @DisplayName("URI를 생성한다.")
    @Test
    void create() {
        URI uri = URI.from("/user?username=hi&password=hello");

        assertAll(
            () -> assertThat(uri.getRawPath()).isEqualTo("/user"),
            () -> assertThat(uri.getRawQuery()).isEqualTo("username=hi&password=hello")
        );
    }

    @DisplayName("URI 생성시 예외 테스트")
    @Test
    void createException() {
        assertAll(
            () -> assertThatThrownBy(() -> URI.from(null))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> URI.from(""))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }
}