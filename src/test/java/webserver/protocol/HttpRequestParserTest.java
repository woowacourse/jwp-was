package webserver.protocol;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestParserTest {
    @DisplayName("requestParams가 없는 request를 parsing해서 정상적으로 HttpRequest 생성")
    @Test
    void parseTest() throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");
        final HttpRequest expected = HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(headers)
            .body(new RequestBody(new HashMap<>()))
            .build();

        final String data = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n"
            + System.lineSeparator();
        final StringReader stringReader = new StringReader(data);
        final BufferedReader reader = new BufferedReader(stringReader);

        assertThat(HttpRequestParser.parse(reader))
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @DisplayName("request를 parsing해서 정상적으로 queryParams를 생성")
    @Test
    void parseTest2() throws IOException {
        final String data =
            "GET /user/create?userId=testId&password=testPW&name=testName&email=test%40test.com HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + System.lineSeparator();
        final StringReader stringReader = new StringReader(data);
        final BufferedReader reader = new BufferedReader(stringReader);

        final HttpRequest httpRequest = HttpRequestParser.parse(reader);

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "testId");
        expected.put("password", "testPW");
        expected.put("name", "testName");
        expected.put("email", "test%40test.com");

        assertThat(httpRequest.getQueryParams()).usingRecursiveComparison().isEqualTo(expected);
    }
}
