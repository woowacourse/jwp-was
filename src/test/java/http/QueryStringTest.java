package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class QueryStringTest {
    private static final String QUERY_STRING = "id=1&name=park&password=1234";

    @Test
    void 쿼리스트링_정상_생성() {
        assertDoesNotThrow(() -> new QueryString(QUERY_STRING));
    }

    @Test
    void 쿼리스트링_가져오기() {
        QueryString queryString = new QueryString(QUERY_STRING);
        assertThat(queryString.getParameter("id")).isEqualTo("1");
        assertThat(queryString.getParameter("name")).isEqualTo("park");
        assertThat(queryString.getParameter("password")).isEqualTo("1234");
    }
}
