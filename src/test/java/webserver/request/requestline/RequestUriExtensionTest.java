package webserver.request.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriExtensionTest {

    @Test
    @DisplayName("올바른 extension 찾기")
    void findExtension() {
        assertThat(RequestUriExtension.findExtension("html")).isEqualByComparingTo(RequestUriExtension.HTML);
    }

    @Test
    @DisplayName("없는 extension일 경우 none을 반환한다.")
    void findExtension_none() {
        assertThat(RequestUriExtension.findExtension("http")).isEqualByComparingTo(RequestUriExtension.NONE);
    }

    @Test
    void isSame() {
        assertThat(RequestUriExtension.HTML.isSame("html")).isTrue();
    }

    @Test
    void isNotSame() {
        assertThat(RequestUriExtension.HTML.isSame("ico")).isFalse();
    }
}