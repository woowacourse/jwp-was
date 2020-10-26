package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import web.request.HttpMethod;
import web.request.RequestUri;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestUriTest {

    @DisplayName("RequestHeader URI 확인")
    @Test
    void getUriTest() {
        String request = "GET /js/chunk-vendors.js HTTP/1.1";

        RequestUri requestUri = new RequestUri(request);

        assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestUri.getRequestPath()).isEqualTo("/js/chunk-vendors.js");
        assertThat(requestUri.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("생성자에 전달받은 값이 비어있는 경우")
    @Test
    void constructor() {
        assertThatThrownBy(() -> new RequestUri(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비어있습니다.");
    }

    @DisplayName("생성자에 전달받은 값이 올바르지 않은 경우")
    @ParameterizedTest
    @CsvSource(value = {"GET", "GET /index.html", "GET /index.html HTTP/1.1 errrorrrororr"})
    void constructor2(String uri) {
        assertThatThrownBy(() -> new RequestUri(uri))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s : 매개변수가 올바르지 않습니다.", uri);
    }
}