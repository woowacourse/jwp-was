package http;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpUriTest {
    @Test
    @DisplayName("HttpUri 생성 테스트")
    void from() {
        Assertions.assertThat(HttpUri.from("/")).isInstanceOf(HttpUri.class);
    }

    @Test
    @DisplayName("HttpUri path, query 분리 테스트")
    void from_has_query() {
        HttpUri httpUri = HttpUri.from("/create?name=홍길동");
        assertThat(httpUri.getPath()).isEqualTo("/create");
        assertThat(httpUri.getParam("name")).isEqualTo("홍길동");
    }
}
