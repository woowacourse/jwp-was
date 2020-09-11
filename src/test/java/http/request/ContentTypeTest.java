package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContentTypeTest {
    @ParameterizedTest
    @CsvSource({
        "APPLICATION_X_WWW_FORM_URLENCODED,%ED%95%9C%EA%B8%80%EC%9D%B8%EC%BD%94%EB%94%A9,한글인코딩",
        "APPLICATION_JSON,input,input"
    })
    void parse(ContentType contentType, String input, String expected) {
        assertThat(contentType.parse(input)).isEqualTo(expected);
    }
}
