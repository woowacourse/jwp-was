package http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestParameterTest {

    @Test
    void of_올바른경우() {
        String parameterLine =
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpRequestParameter httpRequestParameter = HttpRequestParameter.of(parameterLine);

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");

        for (String expectedKey : expectedParameters.keySet()) {
            assertThat(expectedParameters.get(expectedKey))
                    .isEqualTo(httpRequestParameter.getParameter(expectedKey));
        }
    }

    @Test
    void getParameter_키가_존재하지_않는_경우() {
        String parameterLine =
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        HttpRequestParameter httpRequestParameter = HttpRequestParameter.of(parameterLine);

        String notExistKey = "notExist";
        assertThrows(NotFoundHttpRequestHeader.class, () -> httpRequestParameter.getParameter(notExistKey));

    }
}
