package http.request;

import http.parser.HttpUriParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpUriParserTest {

    @Test
    void parse() {
        String uriString = "/user/create?userId=conas&password=1234&name=conatuseus&email=conatuseus@gmail.com";

        assertDoesNotThrow(() -> HttpUriParser.parse(uriString));

        HttpUri uri = HttpUriParser.parse(uriString);
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQuery()).isEqualTo("userId=conas&password=1234&name=conatuseus&email=conatuseus@gmail.com");
    }
}