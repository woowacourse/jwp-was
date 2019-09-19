package utils.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringParserTest {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final String USER_ID_VALUE = "javajigi";
    private static final String PASSWORD_VALUE = "password";
    private static final String NAME_VALUE = "박재성";
    private static final String EMAIL_VALUE = "javajigi@slipp.net";

    private static final String ENCODE_KEY_VALUE = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @Test
    @DisplayName("encode된 값을 key value로 설정하는데 성공한다.")
    void queryStringParser() {
        QueryStringParser queryStringParser = new QueryStringParser();
        Map<String, String> result = queryStringParser.toMap(ENCODE_KEY_VALUE);

        assertThat(result.get(USER_ID)).isEqualTo(USER_ID_VALUE);
        assertThat(result.get(PASSWORD)).isEqualTo(PASSWORD_VALUE);
        assertThat(result.get(NAME)).isEqualTo(NAME_VALUE);
        assertThat(result.get(EMAIL)).isEqualTo(EMAIL_VALUE);
    }
}