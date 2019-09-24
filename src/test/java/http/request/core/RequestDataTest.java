package http.request.core;

import http.exception.CanNotParseDataException;
import http.request.RequestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.StringUtils.*;

class RequestDataTest {

    private RequestPath requestPath;
    private RequestData requestData;

    private Map<String, String> data = new HashMap<>();

    @BeforeEach
    void setUp() {
        data.put("userId", "javajigi");
        data.put("password", "password");
        data.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        data.put("email", "javajigi%40slipp.net");
    }

    @Test
    @DisplayName("파라미터 없는 GET 으로 요청 할 경우 예외처리 한다.")
    void RequestGet() {
        requestPath = new RequestPath(RequestPrefixPath.of(FILE_PATH), FILE_PATH);
        assertThrows(CanNotParseDataException.class, () -> requestData = new RequestData(requestPath));

    }

    @Test
    @DisplayName("파라미터 있는 GET 으로 요청 할 경우 Data 추출 한다.")
    void RequestGetHasParam() {
        requestPath = new RequestPath(RequestPrefixPath.of(GET_PARAMS_PATH), GET_PARAMS_PATH);
        requestData = new RequestData(requestPath);
        requestData.getData().keySet()
                .forEach(key -> assertThat(requestData.getData().get(key)).isEqualTo(data.get(key)));
    }

    @Test
    @DisplayName("Post 로 요청 할 경우 데이터를 추출 한다.")
    void RequestPostHasParam() {
        requestData = new RequestData(POST_BODY);
        requestData.getData().keySet()
                .forEach(key -> assertThat(requestData.getData().get(key)).isEqualTo(data.get(key)));
    }

    @AfterEach
    void tearDown() {
        data.clear();
    }
}