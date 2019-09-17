package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UrlEncodedBodyParserTest {

    @Test
    void parse() {
        String body = "userId=john123&password=p%40ssW0rd&name=john&email=john%40example.com";
        Map<String, String> parsedBody = UrlEncodedBodyParser.parse(body);
        assertThat(parsedBody.get("userId")).isEqualTo("john123");
        assertThat(parsedBody.get("password")).isEqualTo("p@ssW0rd");
        assertThat(parsedBody.get("name")).isEqualTo("john");
        assertThat(parsedBody.get("email")).isEqualTo("john@example.com");
    }
}