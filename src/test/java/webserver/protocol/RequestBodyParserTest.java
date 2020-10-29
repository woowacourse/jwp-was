package webserver.protocol;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyParserTest {

    @DisplayName("정상적으로 RequestBody 생성")
    @Test
    void parseTest() {
        final Map<String, String> contents = new HashMap<>();
        contents.put("userId", "javajigi");
        contents.put("password", "password");
        final RequestBody expected = new RequestBody(contents);

        assertThat(RequestBodyParser.parse("userId=javajigi&password=password"))
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}