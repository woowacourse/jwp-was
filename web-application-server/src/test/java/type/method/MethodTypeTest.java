package type.method;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import exception.NotSupportedMethodException;

class MethodTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"GET,GET", "POST,POST"})
    void find_HTTP_Method(final String method, final MethodType expect) {
        final MethodType actual = MethodType.find(method);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void find_Exception() {
        assertThatThrownBy(() -> MethodType.find("GOT"))
                .isInstanceOf(NotSupportedMethodException.class)
                .hasMessage("지원하지 않는 Method 입니다.");
    }
}