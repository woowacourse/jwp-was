package utils.parser;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringParserTest {
    private static final String USER_ID_KEY_NAME = "userId";
    private static final String PASSWORD_KEY_NAME = "password";
    private static final String NAME_KEY_NAME = "name";
    private static final String EMAIL_KEY_NAME = "email";

    private static final String USER_ID_VALUE = "javajigi";
    private static final String PASSWORD_VALUE = "password";
    private static final String NAME_VALUE = "박재성";
    private static final String EMAIL_VALUE = "javajigi@slipp.net";

    private static final String ENCODED_QUERY_STRING = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @Test
    void queryStringParser() {
        final SimpleStringParser queryStringParser = KeyValueParserFactory.queryStringParser();
        final Map<String, String> result = queryStringParser.interpret(ENCODED_QUERY_STRING);
        assertThat(result.get(USER_ID_KEY_NAME)).isEqualTo(USER_ID_VALUE);
        assertThat(result.get(PASSWORD_KEY_NAME)).isEqualTo(PASSWORD_VALUE);
        assertThat(result.get(NAME_KEY_NAME)).isEqualTo(NAME_VALUE);
        assertThat(result.get(EMAIL_KEY_NAME)).isEqualTo(EMAIL_VALUE);
    }
}