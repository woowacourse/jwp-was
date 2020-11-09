package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestUtilsTest {

    @DisplayName("쿼리스트링 추출")
    @Test
    public void parseQueryString() {
        Map<String, String> queryStringMap = HttpRequestUtils.parseQueryString(
            "userId=javajigi&password=pass");

        assertThat(queryStringMap).containsEntry("userId", "javajigi");
        assertThat(queryStringMap).containsEntry("password", "pass");
    }

    @DisplayName("쿠키 추출")
    @Test
    public void parseCookies() {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(
            "JSESSIONID=123123123;Content-Type=text/woff;");

        assertThat(cookies).containsEntry("JSESSIONID", "123123123");
        assertThat(cookies).containsEntry("Content-Type", "text/woff");
    }
}
