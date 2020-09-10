package utils;

import static org.assertj.core.api.Assertions.*;
import static webserver.HttpRequest.PATH;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @DisplayName("Url로부터 path와 parameters를 분리")
    @Test
    void readUrl() {
        // given
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        Map<String, String> actual = StringUtils.readUrl(url);
        // then
        Map<String, String> expected = new HashMap<>();
        expected.put(PATH, "/user/create");
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        assertThat(actual).isEqualTo(expected);
    }
}
