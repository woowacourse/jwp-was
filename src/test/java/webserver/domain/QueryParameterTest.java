package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParameterTest {
    private final String rawQuery = "userId=javajigi"
            + "&password=password"
            + "&name=%EB%B0%95%EC%9E%AC%EC%84%B1"
            + "&email=javajigi%40slipp.net";

    private final QueryParameter queryParameter = new QueryParameter(rawQuery);

    @Test
    @DisplayName("쿼리 파라미터가 정확하게 생성되는지 확인")
    void makeQueryObject() {
        assertThat(queryParameter.getValue("userId")).isEqualTo("javajigi");
        assertThat(queryParameter.getValue("password")).isEqualTo("password");
        assertThat(queryParameter.getValue("name")).isEqualTo("박재성");
        assertThat(queryParameter.getValue("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("없는 파라미터를 검색했을 때 빈 문자열을 반환")
    void nonExistQueryCheck() {
        assertThat(queryParameter.getValue("asdf")).isEqualTo("");
    }

    @Test
    @DisplayName("빈 쿼리 파라미터가 입력된 경우")
    void emptyQueryCheck() {
        final QueryParameter empty = new QueryParameter("");
        assertThat(empty.getQueries().size()).isEqualTo(0);
    }
}