package http.factory;

import http.HttpMethod;
import http.RequestUri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestUriFactoryTest {
    @DisplayName("생성 테스트 - params가 있는 경우")
    @Test
    void construct() {
        String request = "GET /user/create?userId=id&password=1234 HTTP/1.1\n";
        RequestUri requestUri = RequestUriFactory.getRequestUri(request);

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("userId", "id");
        expectedParams.put("password", "1234");

        assertAll(
                () -> assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestUri.getUrl()).isEqualTo("/user/create"),
                () -> assertThat(requestUri.getParams()).isEqualTo(expectedParams)
        );
    }

    @DisplayName("생성 테스트 - params가 없는 경우")
    @Test
    void constructWithNoParams() {
        String request = "GET /user/create HTTP/1.1\n";
        RequestUri requestUri = RequestUriFactory.getRequestUri(request);

        Map<String, String> expectedParams = new HashMap<>();

        assertAll(
                () -> assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestUri.getUrl()).isEqualTo("/user/create"),
                () -> assertThat(requestUri.getParams()).isEqualTo(expectedParams)
        );
    }
}
