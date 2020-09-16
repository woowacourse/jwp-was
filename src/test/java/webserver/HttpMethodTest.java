package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpMethodTest {

    @DisplayName("서버에서 지원하는 HTTP Method")
    @ParameterizedTest
    @CsvSource({"GET,true", "POST,true", "OPTIONS,false", "TRACE,false"})
    void isSupported(String httpMethod, boolean expected) {
        assertThat(HttpMethod.isSupported(httpMethod)).isEqualTo(expected);
    }
}
