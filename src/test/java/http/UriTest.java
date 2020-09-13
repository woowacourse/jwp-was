package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UriTest {
    @DisplayName("from: uri를 입력받아 인스턴스 생성")
    @Test
    void from() {
        // given
        String uriAndQueryString = "/user/create?userId=test%40test.com&password=1q2w3e&name=hello&email=nullable%40kakao.com";

        // when
        Uri uri = Uri.from(uriAndQueryString);

        // then
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQueryParameters()).isNotNull();
    }
}
