package webserver.protocol;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyParserTest {
    @DisplayName("정상적으로 RequestBody 생성")
    @Test
    void parseTest() throws IOException {
        final Map<String, String> contents = new HashMap<>();
        contents.put("userId", "user");
        contents.put("password", "password");
        contents.put("name", "name");
        contents.put("email", "email@test.com");
        final RequestBody expected = new RequestBody(contents);

        final String data = "userId=user&password=password&name=name&email=email@test.com";
        final StringReader stringReader = new StringReader(data);
        final BufferedReader reader = new BufferedReader(stringReader);

        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", data.getBytes().length + "");

        assertThat(RequestBodyParser.parse(reader, headers))
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}