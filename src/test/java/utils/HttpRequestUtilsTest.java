package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {

    @DisplayName("파라미터 맵으로 파싱")
    @Test
    void parseParamToMap_paramString_equal() {
        String rawParams = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        Map<String, String> params = HttpRequestUtils.parseParamToMap(rawParams);

        assertThat(params.get("userId")).isEqualTo("javajigi");
        assertThat(params.get("password")).isEqualTo("password");
        assertThat(params.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(params.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("빈 쿼리스트링 파싱")
    @Test
    void parseQueryString_noQueryString_empty() {
        String noQueryString = "/user/create";
        Map<String, String> params = HttpRequestUtils.parseQueryString(noQueryString);
        assertThat(params.size()).isEqualTo(0);

    }

    @DisplayName("쿼리 파싱")
    @Test
    void parseAbsPathAndQuery_wholePath_equal() {
        String rawUri = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri).length).isEqualTo(2);
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri)[0]).isEqualTo("/user/create");
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri)[1]).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
