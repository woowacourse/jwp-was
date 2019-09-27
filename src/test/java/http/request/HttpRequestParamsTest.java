package http.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParamsTest {
    private HttpRequestParams requestParams;

    @Test
    @DisplayName("requestParams 생성 테스트")
    void create() {
        assertThat(HttpRequestParams.of()).isInstanceOf(HttpRequestParams.class);
    }

    @Test
    @DisplayName("parameter 값 테스트")
    void getParameterTest() throws IOException {
        requestParams = HttpRequestParams.of();
        requestParams.putAll("logined=true");
        assertThat(requestParams.getParameter("logined")).isEqualTo("true");
    }

    @Test
    @DisplayName("parameter 값 없을경우 테스트")
    void noParameterTest() throws IOException {
        requestParams = HttpRequestParams.of();
        Assertions.assertDoesNotThrow(() -> requestParams.putAll(""));
    }

    @Test
    @DisplayName("parameter 키만 있을 경우 테스트")
    void noValueParameterTest() throws IOException {
        requestParams = HttpRequestParams.of();
        Assertions.assertDoesNotThrow(() -> requestParams.putAll("logined=true&name"));
        assertThat(requestParams.getParameter("name")).isNull();
    }

    @Test
    @DisplayName("parameter 중복 키가 있을 경우 테스트")
    void duplicatedKeyParameterTest() throws IOException {
        requestParams = HttpRequestParams.of();
        Assertions.assertDoesNotThrow(() -> requestParams.putAll("logined=true&logined=false"));
        assertThat(requestParams.getParameter("logined")).isEqualTo("true");
    }
}