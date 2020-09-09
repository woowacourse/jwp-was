package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestUtilsTest {

    private BufferedReader REQUEST_BUFFERED_READER;

    private static final String REQUEST =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final String POST_REQUEST_HEADER =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n";

    private static final String POST_REQUEST_BODY = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final String GET_REQUEST_HEADER =
        "GET /user/create?userId=javajigi&password=password&name=%ED%8F%AC%EB%B9%84&email=javajigi%40slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-length: 10"
            + "Accept: */*";
    private static final String GET_WHOLE_URL = "/user/create?userId=javajigi&password=password&name=포비&email=javajigi@slipp.net";
    private static final String GET_WHOLE_URL_WITHOUT_PARAMS = "/user/create";
    private static final String GET_PATH = "/user/create";
    private static final Map<String, String> GET_PARAMS = new HashMap<String, String>() {{
        put("userId", "javajigi");
        put("password", "password");
        put("name", "포비");
        put("email", "javajigi@slipp.net");
    }};

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
    }

    @Test
    public void extractRequestHeader() throws IOException {
        String header = RequestUtils.extractHeader(REQUEST_BUFFERED_READER);
        assertThat(header).isEqualTo(POST_REQUEST_HEADER);
    }

    @Test
    public void extractHeaderValue() {
        String contentLength = RequestUtils.extractHeaderValue(POST_REQUEST_HEADER, "Content-Length");
        assertThat(contentLength).isEqualTo("93");
    }

    @Test
    public void extractRequestHeaderNotExist() {
        assertThatThrownBy(() -> RequestUtils.extractHeaderValue(POST_REQUEST_HEADER, "not-existing"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("키에 해당하는 헤더 값이 존재 하지 않습니다");
    }

    @Test
    public void extractRequestBody() throws IOException {
        BufferedReader request = REQUEST_BUFFERED_READER;
        String header = RequestUtils.extractHeader(request);

        String contentLength = RequestUtils.extractHeaderValue(header, "Content-Length");
        String requestBody = RequestUtils.extractBody(request, contentLength);
        assertThat(requestBody).isEqualTo(POST_REQUEST_BODY);
    }

    @Test
    public void extractWholeUrl() {
        String wholeUrl = RequestUtils.extractWholeUrl(GET_REQUEST_HEADER);
        assertThat(wholeUrl).isEqualTo(GET_WHOLE_URL);
    }

    @Test
    public void extractPath() {
        String path = RequestUtils.extractPath(GET_WHOLE_URL);
        assertThat(path).isEqualTo(GET_PATH);
    }

    @Test
    public void extractParams() {
        Map<String, String> params = RequestUtils.extractParams(GET_WHOLE_URL);
        assertThat(params.equals(GET_PARAMS));
    }

    @Test
    public void paramsNotExist() {
        assertThatThrownBy(() -> RequestUtils.extractParams(GET_WHOLE_URL_WITHOUT_PARAMS))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿼리 파라미터가 존재하지 않습니다");
    }
}