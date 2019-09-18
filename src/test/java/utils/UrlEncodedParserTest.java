package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UrlEncodedParserTest {

    @Test
    void parse() {
        String body = "userId=john123&password=p%40ssW0rd&name=john&email=john%40example.com";
        Map<String, String> parsedBody = UrlEncodedParser.parse(body);
        assertThat(parsedBody.get("userId")).isEqualTo("john123");
        assertThat(parsedBody.get("password")).isEqualTo("p@ssW0rd");
        assertThat(parsedBody.get("name")).isEqualTo("john");
        assertThat(parsedBody.get("email")).isEqualTo("john@example.com");
    }
}