package webserver.protocol;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderParserTest {
    @DisplayName("requestParams가 없는 request를 parsing해서 정상적으로 RequestHeader 생성")
    @Test
    void parseTest() {
        final HttpMethod httpMethod = HttpMethod.GET;
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();
        final RequestHeader expected = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);
        final List<String> header = Collections.singletonList("GET /index.html HTTP/1.1");

        assertThat(RequestHeaderParser.parse(header)).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("request를 parsing해서 정상적으로 queryParams를 생성")
    @Test
    void parseTest2() {
        final List<String> header = Collections.singletonList(
            "GET /user/create?userId=testId&password=testPW&name=testName&email=test%40test.com HTTP/1.1");

        final RequestHeader requestHeader = RequestHeaderParser.parse(header);

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "testId");
        expected.put("password", "testPW");
        expected.put("name", "testName");
        expected.put("email", "test%40test.com");

        assertThat(requestHeader.getQueryParams()).usingRecursiveComparison().isEqualTo(expected);
    }
}
