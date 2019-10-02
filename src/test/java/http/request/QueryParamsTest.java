package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {
    @Test
    @DisplayName("생성")
    void create() {
        QueryParams queryParams = QueryParams.of("userId=woowa&password=password&name=woo");

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    @DisplayName("동일한 key가 여러개인 경우 getParam 메서드는 첫번째값 반환")
    void getParam() {
        QueryParams queryParams = QueryParams.of("userId=woowa&userId=&userId=&password=password&name=woo");

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    @DisplayName("동일한 key가 여러개인 경우 getParams 메서드는 리스트 반환")
    void getParams() {
        QueryParams queryParams =
                QueryParams.of("userId=woowa1&userId=woowa2&userId=woowa3&password=password&name=woo");

        assertThat(queryParams.getParams("userId"))
                .isEqualTo(Arrays.asList("woowa1", "woowa2", "woowa3"));
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }
}