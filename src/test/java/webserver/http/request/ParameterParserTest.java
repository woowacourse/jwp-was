package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterParserTest {
    @DisplayName("Parameter 파싱 테스트")
    @Test
    void queryParsingTest() throws IOException {
        Map<String, String> actual
                = ParameterParser.parse("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertThat(actual).hasSize(4);
        assertThat(actual.get("userId")).isEqualTo("javajigi");
        assertThat(actual.get("password")).isEqualTo("password");
        assertThat(actual.get("name")).isEqualTo("박재성");
        assertThat(actual.get("email")).isEqualTo("javajigi@slipp.net");
    }
}
