package http.common;

import http.common.exception.InvalidURLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URLTest {
    @Test
    void 생성_성공() {
        String path = "/test";
        String queryString = "id=1";
        URL url = URL.of(String.format("%s?%s", path, queryString));

        assertThat(url.getPath()).isEqualTo(path);
        assertThat(url.getQueryString()).isEqualTo(queryString);
    }

    //TODO: TestCase 통과하도록 URL 검증 로직 추가
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n", "?id=1"})
    void 생성_실패(String input) {
        assertThrows(InvalidURLException.class, () -> URL.of(input));
    }
}