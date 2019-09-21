package http;

import http.request.HttpUri;
import http.request.HttpUriParser;
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
}