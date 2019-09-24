package http.request;

import http.common.Parameters;
import http.utils.HttpUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private static Stream<Arguments> provideHttpRequest() {
        return Stream.of(
                Arguments.of("GET /user/create?userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*"),
                        HttpUtils.parseQuery("userId=aiden&password=password&name=aiden&email=aiden@aiden.com"),
                        "http://localhost:8080/user/create",
                        true,
                        "aiden"),
                Arguments.of("POST /user/create HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Content-Length: 30",
                                "Content-Type: application/x-www-form-urlencoded", "Accept: */*"),
                        HttpUtils.parseQuery("userId=cony&password=password&name=cony&email=cony@cony.com"),
                        "http://localhost:8080/user/create",
                        false,
                        "cony")
        );
    }

    @ParameterizedTest
    @MethodSource("provideHttpRequest")
    public void httpRequestTest(String requestLine, List<String> header, Map<String, String> params,
                                String url, boolean isGet, String userId) throws IOException {
        Parameters parameters = new Parameters();
        parameters.addAll(params);
        HttpRequest httpRequest = HttpRequest.of(RequestLine.of(requestLine), RequestHeader.of(header), parameters);

        assertThat(httpRequest.getUrl()).isEqualTo(url);
        assertThat(httpRequest.isGet()).isEqualTo(isGet);
        assertThat(httpRequest.getParameter("userId")).isEqualTo(userId);
    }
}