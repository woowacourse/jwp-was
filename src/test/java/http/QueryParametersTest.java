package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryParametersTest {
    @DisplayName("from: 쿼리스트링을 받아 인스턴스를 생성한다.")
    @Test
    void from() {
        // given
        String queries = "userId=test@test.com&password=1q2w3e&name=hello&email=nullable@kakao.com";

        // when
        QueryParameters queryParameters = QueryParameters.from(queries);

        // then
        assertAll(
                () -> assertThat(queryParameters.getParameter("userId")).isEqualTo("test@test.com"),
                () -> assertThat(queryParameters.getParameter("password")).isEqualTo("1q2w3e"),
                () -> assertThat(queryParameters.getParameter("name")).isEqualTo("hello"),
                () -> assertThat(queryParameters.getParameter("email")).isEqualTo("nullable@kakao.com")
        );

    }
}
