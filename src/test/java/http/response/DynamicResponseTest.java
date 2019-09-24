package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

class DynamicResponseTest {
    private List<Object> firstLineTokens;
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        DATA.put("userId", "javajigi");
        DATA.put("password", "password");
        DATA.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        DATA.put("email", "javajigi%40slipp.net");
    }

    @Test
    @DisplayName("파라미터를 가지고 있는 경우 GET 동적 파일 테스트")
    void hasParamDynamicGetTest() throws IOException, URISyntaxException {
        firstLineTokens = Arrays.asList(GET_METHOD, REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new DynamicResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(FOUND_BODY);
    }

    @Test
    @DisplayName("파라미터를 가지고 있지 경우 POST 동적 파일 테스트")
    void hasDynamicPostTest() throws IOException, URISyntaxException {
        firstLineTokens = Arrays.asList(POST_METHOD, REQUEST_POST_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, POST_REQUEST_HEADER, DATA);
        Response response = new DynamicResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(FOUND_BODY);
    }

    @Test
    @DisplayName("파라미터를 가지고 있지 않는 경우 동적 파일 테스트")
    void hasNotDynamicTest() throws IOException, URISyntaxException {
        DATA.clear();
        firstLineTokens = Arrays.asList(GET_METHOD, REQUEST_GET_PATH, REQUEST_VERSION);
        httpRequest = new HttpRequest(firstLineTokens, GET_REQUEST_HEADER, DATA);
        Response response = new DynamicResponseCreator().create(httpRequest);
        ResponseBody responseBody = response.doResponse();

        assertThat(responseBody.getBody().get(0).toString()).isEqualTo(OK_BODY);
    }

    @AfterEach
    void tearDown() {
        DATA.clear();
    }
}