package http.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class URLTest {
    @Test
    void 생성_성공() {
        String path = "/test";
        String queryString = "id=1";
        URL url = URL.of(String.format("%s?%s", path, queryString));

        assertThat(url.getPath()).isEqualTo(path);
        assertThat(url.getQueryString()).isEqualTo(queryString);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void null_또는_공백시_빈_URL_생성(String url) {
        assertThat(URL.of(url)).isEqualTo(URL.EMPTY_URL);
    }
}