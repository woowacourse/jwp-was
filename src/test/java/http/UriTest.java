package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UriTest {
    @DisplayName("from: query parameter가 있는 uri를 입력받아 인스턴스 생성")
    @Test
    void from_queryStringExist() {
        // given
        String uriWithQueryString = "/user/create?userId=test%40test.com&password=1q2w3e&name=hello&email=nullable%40kakao.com";

        // when
        Uri uri = Uri.from(uriWithQueryString);

        // then
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQueryParameters()).isNotNull();
    }

    @DisplayName("from: query parameter가 없는 uri를 입력받아 인스턴스 생성")
    @Test
    void from_queryStringNotExist() {
        // given
        String uriWithoutQueryString = "/user/read";

        // when
        Uri uri = Uri.from(uriWithoutQueryString);

        // then
        assertThat(uri.getPath()).isEqualTo("/user/read");
        assertThat(uri.getQueryParameters()).isNull();
    }
}
