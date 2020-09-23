package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestMethodTest {
    @DisplayName("생성 테스트")
    @Test
    void of() {
        assertThat(RequestMethod.of("GET")).isEqualTo(RequestMethod.GET);
    }

    @DisplayName("지원하지 않는 메소드가 올 시 null 반환")
    @Test
    void ofException() {
        assertThat(RequestMethod.of("hi")).isNull();
    }

    @DisplayName("Http Method가 body를 지원하는 지 확인한다.")
    @Test
    void allowBody() {
        assertThat(RequestMethod.GET.allowBody()).isFalse();
        assertThat(RequestMethod.POST.allowBody()).isTrue();
    }
}