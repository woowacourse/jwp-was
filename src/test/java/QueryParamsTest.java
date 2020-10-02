import mapper.QueryParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {

    @DisplayName("쿼리스트링을 잘 분해하는지 테스트")
    @Test
    void getQueryParamsTest() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        QueryParams mapper = new QueryParams(url);
        Map<String, String> queryParams = mapper.getQueryParams();

        assertThat(queryParams).hasSize(4);
    }
}