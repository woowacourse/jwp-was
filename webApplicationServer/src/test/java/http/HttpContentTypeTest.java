package http;

import http.header.HttpContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpContentTypeTest {

    @DisplayName("HTTP Content-Type name에 상관없이 HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"application/x-www-form-urlencoded", "text/xml", "Application/ogg", "text/plain"})
    void fromTest(String name) {
        assertThat(HttpContentType.from(name)).isEqualTo(HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED);
    }
}