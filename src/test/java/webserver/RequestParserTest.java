package webserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private static final byte[] REQUEST_BYTE =
        ("POST / HTTP/1.1\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Accept: */*\r\n" +
            "Cache-Control: no-cache\r\n" +
            "Host: localhost:8080\r\n" +
            "Content-Length: 26\r\n" +
            "Cookie: JSESSIONID=471133FFBC577D4F5BCB5F45AE944BF7; logined=true\r\n" +
            "\r\n" +
            "message=This%20is%20body").getBytes();

    @Test
    void parse() throws IOException {
        HttpRequest req = RequestParser.parse(new ByteArrayInputStream(REQUEST_BYTE));

        assertThat(req.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(req.getUrl()).isEqualTo("/");
        assertThat(req.getHeader("Content-Length")).isEqualTo("26");
        assertThat(((String) req.getBody().get("message")).trim()).isEqualTo("This is body");
    }

    @Test
    void querystring() throws IOException {
        HttpRequest req = RequestParser.parse(new ByteArrayInputStream("GET /some-resource?q=abcde&email=john%40example.com".getBytes()));

        assertThat(req.getUrl()).isEqualTo("/some-resource?q=abcde&email=john%40example.com");
        assertThat(req.getPath()).isEqualTo("/some-resource");
        assertThat(req.getQuery("q")).isEqualTo("abcde");
        assertThat(req.getQuery("email")).isEqualTo("john@example.com");
    }

    @Test
    void parse_cookie() throws IOException {
        HttpRequest req = RequestParser.parse(new ByteArrayInputStream(REQUEST_BYTE));

        assertThat(req.getCookie("logined")).isEqualTo("true");
    }
}
