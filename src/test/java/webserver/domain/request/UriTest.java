package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UriTest {
    @DisplayName("Uri의 파라미터를 올바로 분리한다.")
    @Test
    void of_shouldSplitParameters() {
        Uri uri = Uri.of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");

        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getParameters()).isEqualTo(expectedParameters);
    }

    @DisplayName("파라미터의 value가 빈값인 경우에도 분리한다.")
    @Test
    void of_whenEmptyValue_shouldSplitParameters() {
        Uri uri = Uri.of("/user/create?userId=&password=&name=&email=");

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "");
        expectedParameters.put("password", "");
        expectedParameters.put("name", "");
        expectedParameters.put("email", "");

        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getParameters()).isEqualTo(expectedParameters);
    }
}