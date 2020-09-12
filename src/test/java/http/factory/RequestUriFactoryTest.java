package http.factory;

import http.HttpMethod;
import http.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestUriFactoryTest {
    @DisplayName("생성 테스트")
    @Test
    void construct() {
        String request = "GET /user/create?userId=id&password=1234 HTTP/1.1\n";
        HashMap<String, String> params = new HashMap<>();
        RequestLine requestLine = RequestUriFactory.createRequestUri(request, params);

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("userId", "id");
        expectedParams.put("password", "1234");

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getUrl()).isEqualTo("/user/create"),
                () -> assertThat(params).isEqualTo(expectedParams)
        );
    }
}
