package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpMethodTest {

    @DisplayName("HTTP Method - 서버에서 지원하는가")
    @ParameterizedTest
    @CsvSource({"GET,true", "POST,true", "PUT,false", "DELETE,false", "HEAD,false", "TRACE,false",
        "CONNECT,false", "OPTIONS,false"})
    void isSupported(String httpMethod, boolean expected) {
        assertThat(HttpMethod.find(httpMethod).isSupport()).isEqualTo(expected);
    }

    @DisplayName("HTTP Method - body 유무")
    @ParameterizedTest
    @CsvSource({"GET,false", "POST,true", "PUT,true", "DELETE,false", "HEAD,false", "TRACE,false",
        "CONNECT,false", "OPTIONS,false"})
    void hasBody(String httpMethod, boolean expected) {
        assertThat(HttpMethod.hasBody(httpMethod)).isEqualTo(expected);
    }

    @DisplayName("같은 HTTP Method 찾기")
    @Test
    void isSame() {
        String httpMethod = "GET";
        assertThat(HttpMethod.find(httpMethod).isSame("GET")).isTrue();
    }

    @DisplayName("HTTP Method 찾기 - 해당하는 HTTP Method를 찾지 못하면 null 반환")
    @Test
    void find_NotExistHttpMethod_ThrownException() {
        String httpMethod = "HELLO";
        assertThat(HttpMethod.find(httpMethod)).isNull();
    }
}
