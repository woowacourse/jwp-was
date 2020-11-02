package webserver.protocol;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestParserTest {
    @DisplayName("requestParams가 없는 request를 parsing해서 정상적으로 HttpRequest 생성")
    @Test
    void parseTest() {
        final HttpRequest expected = HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build();
        final List<String> header = Collections.singletonList("GET /index.html HTTP/1.1");

        assertThat(HttpRequestParser.parse(header)).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("request를 parsing해서 정상적으로 queryParams를 생성")
    @Test
    void parseTest2() {
        final List<String> header = Collections.singletonList(
            "GET /user/create?userId=testId&password=testPW&name=testName&email=test%40test.com HTTP/1.1");

        final HttpRequest httpRequest = HttpRequestParser.parse(header);

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "testId");
        expected.put("password", "testPW");
        expected.put("name", "testName");
        expected.put("email", "test%40test.com");

        assertThat(httpRequest.getQueryParams()).usingRecursiveComparison().isEqualTo(expected);
    }
}
