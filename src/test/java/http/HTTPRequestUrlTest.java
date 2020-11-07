package http;

import static http.HTTPRequestUrl.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HTTPRequestUrlTest {

    @Test
    void extractRequestUrlTest() {
        String request = "GET /someUrl.url HTTP/1.1";
        assertThat(extractRequestUrl(request).getUrl()).isEqualTo("/someUrl.url");
    }

    @Test
    void extractPathTest() {
        String url = "/someUrl.url";
        HTTPRequestUrl requestUrl = new HTTPRequestUrl("/someUrl.url");
        assertThat(requestUrl.extractClassPath()).isEqualTo("./templates" + url);
    }
}
