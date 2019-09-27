package http.request;

import http.parser.HttpUriParser;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpUriTest {

    @Test
    void create_uri() {
        String uri = "/index.html";

        assertDoesNotThrow(() -> new HttpUri(new URI(uri)));
    }

    @Test
    void get_Path() throws URISyntaxException {
        HttpUri uri = HttpUriParser.parse("/user/create?userId=conas&password=password");

        assertThat(uri.getPath()).isEqualTo("/user/create");
    }

    @Test
    void get_Query() throws URISyntaxException {
        HttpUri uri = HttpUriParser.parse("/user/create?userId=conas&password=password");

        assertThat(uri.getQuery()).isEqualTo("userId=conas&password=password");
    }

    @Test
    void isMatches() {
        String regex = "^.+\\.([a-zA-Z]+)$";
        HttpUri uri = HttpUriParser.parse("/index.html");

        assertThat(uri.isMatches(regex)).isTrue();
    }

    @Test
    void not_is_matches() {
        String regex = "^.+\\.([a-zA-Z]+)$";
        HttpUri uri = HttpUriParser.parse("abcdefg");

        assertThat(uri.isMatches(regex)).isFalse();
    }

    @Test
    void isEqualsPath() {
        HttpUri uri = HttpUriParser.parse("/index.html");
        assertThat(uri.isEqualsPath(HttpUriParser.parse("/index.html"))).isTrue();
    }
}