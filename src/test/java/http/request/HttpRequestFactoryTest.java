package http.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestFactoryTest {
    private static final Map<String, String> testHeaders = new HashMap<>();
    private static final String GET_FIRST_LINE = "GET /index.html HTTP/1.1";
    private static final String POST_FIRST_LINE = "POST /user/create HTTP/1.1";
    private static final List<String> GET_LINES = Arrays.asList(
            "Host: localhost:8080\n",
            "Connection: keep-alive\n",
            "Accept: */*"
    );
    private static final List<String> POST_LiNES = Arrays.asList(
            "Host: localhost:8080\n",
            "Connection: keep-alive\n",
            "Content-Length: 59\n",
            "Content-Type: application/x-www-form-urlencoded\n",
            "Accept: */*");

    private String firstLine;

    @Test
    @DisplayName("해당 GET 메소드가 나오는지 확인 한다.")
    void checkGetMethod() throws IOException {
        firstLine = GET_FIRST_LINE;
        testHeaders.put("Host", "localhost:8080\n");
        testHeaders.put("Connection", "keep-alive\n");
        testHeaders.put("Accept", "*/*");

        Request request = HttpRequestFactory.getRequest(firstLine, GET_LINES);
        assertThat(request.getClass()).isEqualTo(GetRequest.class);
        assertThat(request.getRequestPath().getPath()).isEqualTo("../resources/templates/index.html");
        assertThat(request.getRequestHeader().getHeaders()).isEqualTo(testHeaders);
    }

    @Test
    @DisplayName("해당 POST 메소드가 나오는지 확인 한다.")
    void checkPostMethod() throws IOException {
        firstLine = POST_FIRST_LINE;
        testHeaders.put("Host", "localhost:8080\n");
        testHeaders.put("Connection", "keep-alive\n");
        testHeaders.put("Content-Length", "59\n");
        testHeaders.put("Content-Type", "application/x-www-form-urlencoded\n");
        testHeaders.put("Accept", "*/*");

        Request request = HttpRequestFactory.getRequest(firstLine, POST_LiNES);
        assertThat(request.getClass()).isEqualTo(PostRequest.class);
        assertThat(request.getRequestPath().getPath()).isEqualTo("../resources/templates/user/create");
        assertThat(request.getRequestHeader().getHeaders()).isEqualTo(testHeaders);
    }
}