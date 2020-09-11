package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class URLUtilsTest {

    @DisplayName("requestLine 에서 path를 추출할 수 있다.")
    @Test
    void extractPath() {
        String requestLine = "GET /index.html HTTP/1.1";
        String path = URLUtils.extractPath(requestLine);

        assertThat(path).isEqualTo("./templates/index.html");
    }

    @DisplayName("requestLine 에서 파라미터들을 추출할 수 있다.")
    @Test
    void extractParameters() {
        String requestLine = "GET /user/create?userId=a&password=b&name=c&email=d%40d HTTP/1.1";
        final Map<String, String> expected = new HashMap<>();
        expected.put("userId", "a");
        expected.put("password", "b");
        expected.put("name", "c");
        expected.put("email", "d%40d");


        final Map<String, String> parameters = URLUtils.extractParameters(requestLine);

        assertEquals(expected, parameters);
    }
}