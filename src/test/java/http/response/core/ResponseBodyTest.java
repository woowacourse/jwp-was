package http.response.core;

import http.request.HttpRequest;
import http.request.core.RequestPath;
import http.request.core.RequestPrefixPath;
import http.request.core.RequestVersion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringUtils.*;

class ResponseBodyTest {
    private static final RequestPath REQUEST_PATH = new RequestPath(
            RequestPrefixPath.of(GET_PATH), GET_PATH);
    private static final RequestPath REQUEST_PARAM_PATH = new RequestPath(
            RequestPrefixPath.of(GET_PARAMS_PATH), GET_PARAMS_PATH);
    private static final RequestVersion REQUEST_VERSION = RequestVersion.of(VERSION);
    private static final Map<String, String> DATA = new HashMap<>();

    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;
    private ResponseStatus responseStatus;
    private String headerLine;

    private ResponseBody responseBody;

    @Test
    @DisplayName("상태 OK 일때 getBody() 확인 테스트")
    void OKBody() throws IOException, URISyntaxException {
        firstLineTokens = Arrays.asList(GET_METHOD, REQUEST_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        responseStatus = ResponseStatus.of(200);
        headerLine = ResponseContentType.of(REQUEST_PATH)
                .getContentType();
        responseBody = new ResponseBody(httpRequest, responseStatus, headerLine);

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_BODY);
    }

    @Test
    @DisplayName("상태 Found 일때 getBody() 확인 테스트")
    void FoundBody() throws IOException, URISyntaxException {
        DATA.put("userId", "javajigi");
        DATA.put("password", "password");
        DATA.put("name", "JaeSung");

        firstLineTokens = Arrays.asList(GET_PARAMS_PATH, REQUEST_PARAM_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        responseStatus = ResponseStatus.of(302);
        headerLine = "Location: http://localhost:8080/\r\n";
        responseBody = new ResponseBody(httpRequest, responseStatus, headerLine);

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(FOUND_BODY);
    }

    @AfterEach
    void tearDown() {
        DATA.clear();
    }
}