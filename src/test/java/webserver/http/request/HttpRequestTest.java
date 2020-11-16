package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.HttpRequestFixture;

class HttpRequestTest {
    @DisplayName("GET 요청에 대한 HttpRequest 객체를 생성한다. ")
    @Test
    void of_whenRequestMethodIsGet() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");
        assertThat(httpRequest.getDefaultPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getParameters()).isEqualTo(expectedParameters);
    }
}