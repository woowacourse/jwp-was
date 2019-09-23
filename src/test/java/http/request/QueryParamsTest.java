package http.request;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {
    @Test
    void 생성() {
        QueryParams queryParams = QueryParams.of("userId=woowa&password=password&name=woo");

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void 동일한_key가_여러개인_경우_getParam_메서드는_첫번째값_반환() {
        QueryParams queryParams = QueryParams.of("userId=woowa&userId=&userId=&password=password&name=woo");

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void 동일한_key가_여러개인_경우_getParams_메서드는_리스트_반환() {
        QueryParams queryParams =
                QueryParams.of("userId=woowa1&userId=woowa2&userId=woowa3&password=password&name=woo");

        assertThat(queryParams.getParams("userId"))
                .isEqualTo(Arrays.asList("woowa1", "woowa2", "woowa3"));
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }
}