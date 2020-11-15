package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryParametersTest {
    @DisplayName("queryString으로 파라미터 생성")
    @Test
    void ofTest() {
        String queryString = "userId=javajigi&" +
                "password=password&" +
                "name=%EB%B0%95%EC%9E%AC%EC%84%B1&" +
                "email=javajigi%40slipp.net";
        QueryParameters queryParameters = QueryParameters.notEmptyQueryParameters(queryString);

        Map<String, String> expected = queryParameters.getQueryParameters();

        assertAll(
                () -> assertThat(expected.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(expected.get("password")).isEqualTo("password"),
                () -> assertThat(expected.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1"),
                () -> assertThat(expected.get("email")).isEqualTo("javajigi%40slipp.net")
        );
    }
}
