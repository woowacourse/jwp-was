package jwp.was.webserver.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jwp.was.webserver.exception.NotExistsUrlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UrlPathTest {

    private static final String URL_PATH_DELIMITER = "?";

    @DisplayName("URL에서 URL PATH 생성")
    @ParameterizedTest
    @ValueSource(strings = {"", "?", "?key=value"})
    void from_Success(String queryString) {
        String expectedUrlPath = "index.html";
        UrlPath urlPath = UrlPath.from(expectedUrlPath + queryString);

        assertThat(urlPath.getUrlPath()).isEqualTo(expectedUrlPath);
    }

    @DisplayName("URL에 URL PATH가 없으면 예외 발생")
    @Test
    void from_NotExistsUrl_ThrownException() {
        String wrongUrl = "?key=value";

        assertThatThrownBy(() -> UrlPath.from(wrongUrl)).isInstanceOf(NotExistsUrlPath.class);
    }

    @DisplayName("? 기준으로 urlPath, queryString Split")
    @Test
    void getUrlPathAndQueryStrings() {
        String urlPath = "/urlPath";
        String queryStrings = "key1=value1&key2=value";

        String[] urlPathAndQueryStrings
            = UrlPath.getUrlPathAndQueryStrings(urlPath + URL_PATH_DELIMITER + queryStrings);

        assertThat(urlPathAndQueryStrings.length).isEqualTo(2);
        assertThat(urlPathAndQueryStrings[0]).isEqualTo(urlPath);
        assertThat(urlPathAndQueryStrings[1]).isEqualTo(queryStrings);
    }
}
