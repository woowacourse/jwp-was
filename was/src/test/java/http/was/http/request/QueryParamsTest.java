package http.was.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryParamsTest {

    @DisplayName("쿼리스트링을 잘 분해하는지 테스트")
    @Test
    void getQueryParamsTest() {
        String url = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";

        QueryParams queryParams = new QueryParams(url);

        assertThat(queryParams.getParam("userId")).isEqualTo("javajigi");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("박재성");
        assertThat(queryParams.getParam("email")).isEqualTo("javajigi@slipp.net");
    }
}
