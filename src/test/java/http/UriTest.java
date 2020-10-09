package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UriTest {
    @DisplayName("from: query parameter가 있는 uri를 입력받아 인스턴스를 생성한다.")
    @Test
    void from_queryStringExist() {
        // given
        String uriWithQueryString = "/user/create?userId=test%40test.com&password=1q2w3e&name=hello&email=nullable%40kakao.com";

        // when
        Uri uri = Uri.from(uriWithQueryString);

        // then
        assertAll(
                () -> assertThat(uri.getPath()).isEqualTo("/user/create"),
                () -> assertThat(uri.getParameter("userId")).isEqualTo("test%40test.com"),
                () -> assertThat(uri.getQueryParameters()).isNotNull()
        );
    }

    @DisplayName("from: query parameter가 없는 uri를 입력받아 인스턴스를 생성한다.")
    @Test
    void from_queryStringNotExist() {
        // given
        String uriWithoutQueryString = "/user/read";

        // when
        Uri uri = Uri.from(uriWithoutQueryString);

        // then
        assertAll(
                () -> assertThat(uri.getPath()).isEqualTo("/user/read"),
                () -> assertThat(uri.getQueryParameters()).isNull()
        );
    }
}
