package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {

    @DisplayName("정상적인 Uri로 QueryParams 생성")
    @Test
    void constructor() {
        String requestUri = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        QueryParams actual = new QueryParams(requestUri);

        assertThat(actual.getQueryParams()).isEqualTo(expected);
    }

    @DisplayName("전달 받은 Query String이 없을 때 QueryParams 생성")
    @ValueSource(strings = {"/user/create?", "/user/create"})
    @ParameterizedTest
    void constructorTest(String requestUri) {
        QueryParams queryParams = new QueryParams(requestUri);

        assertThat(queryParams.getQueryParams()).isEmpty();
    }
}