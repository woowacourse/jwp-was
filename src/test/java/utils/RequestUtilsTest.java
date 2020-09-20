package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestUtilsTest {

    @DisplayName("queryString을 Map으로 잘 변환하는지 테스트")
    @Test
    void extractQueryString() {
        String input = "userId=javajigi&password=password&name=name&email=javajigi@slipp.net";
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "name");
        expected.put("email", "javajigi@slipp.net");

        Map<String, String> actual = RequestUtils.extractQueryString(input);

        assertThat(actual).isEqualTo(expected);
    }
}
