package http.model.common;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpHeadersTest {

    @Test
    void 기본생성자_null_safe() {
        assertDoesNotThrow(() -> new HttpHeaders().getHeaders());
    }

    @Test
    void add호출시_내부맵_변경_테스트() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader("key", "value");

        assertThat(httpHeaders.getHeader("key")).isEqualTo("value");
    }

    @Test
    void 외부에서_내부맵_변경불가_테스트() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> map = httpHeaders.getHeaders();

        assertThatThrownBy(() -> map.put("some", "value")).isInstanceOf(UnsupportedOperationException.class);
    }
}