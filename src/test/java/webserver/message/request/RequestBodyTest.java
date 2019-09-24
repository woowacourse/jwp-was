package webserver.message.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    private final String rawQuery = "userId=javajigi"
            + "&password=password"
            + "&name=%EB%B0%95%EC%9E%AC%EC%84%B1"
            + "&email=javajigi%40slipp.net";

    private final RequestBody requestBody = new RequestBody(rawQuery);

    @Test
    @DisplayName("쿼리 파라미터가 정확하게 생성되는지 확인")
    void makeQueryObject() {
        assertThat(requestBody.getQueryValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getQueryValue("password")).isEqualTo("password");
        assertThat(requestBody.getQueryValue("name")).isEqualTo("박재성");
        assertThat(requestBody.getQueryValue("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("없는 파라미터를 검색했을 때 빈 문자열을 반환")
    void nonExistQueryCheck() {
        assertThat(requestBody.getQueryValue("asdf")).isEqualTo("");
    }

    @Test
    @DisplayName("빈 쿼리 파라미터가 입력된 경우")
    void emptyQueryCheck() {
        final RequestBody empty = new RequestBody("");
        assertThat(empty.getQueries().size()).isEqualTo(0);
    }
}