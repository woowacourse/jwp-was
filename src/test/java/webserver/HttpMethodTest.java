package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpMethodTest {

    @DisplayName("HTTP Method - 서버에서 지원하는가")
    @ParameterizedTest
    @CsvSource({"GET,true", "POST,true", "PUT,false", "DELETE,false", "HEAD,false", "TRACE,false", "CONNECT,false", "OPTIONS,false"})
    void isSupported(String httpMethod, boolean expected) {
        assertThat(HttpMethod.isSupported(httpMethod)).isEqualTo(expected);
    }

    @DisplayName("HTTP Method - body 유무")
    @ParameterizedTest
    @CsvSource({"GET,false", "POST,true", "PUT,true", "DELETE,false", "HEAD,false", "TRACE,false", "CONNECT,false", "OPTIONS,false"})
    void hasBody(String httpMethod, boolean expected) {
        assertThat(HttpMethod.hasBody(httpMethod)).isEqualTo(expected);
    }
}
