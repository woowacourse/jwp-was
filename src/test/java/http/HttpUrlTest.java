package http;

import static http.HttpUrl.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpUrlTest {

    @Test
    void extractRequestUrlTest() {
        String request = "GET /someUrl.url HTTP/1.1";
        assertThat(extractRequestUrl(request).getUrl()).isEqualTo("/someUrl.url");
    }

    @Test
    void extractPathTest() {
        String url = "/someUrl.url";
        HttpUrl requestUrl = HttpUrl.from("/someUrl.url");
        assertThat(requestUrl.extractClassPath()).isEqualTo("./templates" + url);
    }
}
