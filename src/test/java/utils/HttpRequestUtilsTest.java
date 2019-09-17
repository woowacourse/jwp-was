package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestUtilsTest {
    final String INDEX_URL = "/index.html";

    @Test
    void parseURLPath_indexUrl_true() {
        String URL = INDEX_URL;
        String line = "GET " + URL + " HTTP/1.1";
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(line)).isEqualTo(URL);
    }

    @Test
    void parseURLPath_emptyUrl_error() {
        String line = "GET HTTP/1.1";
        assertThrows(IllegalArgumentException.class, () -> {
            HttpRequestUtils.parseAbsPathAndQuery(line);
        });
    }

    @Test
    void generateFilePath_index_true() {
        String URL = INDEX_URL;
        String line = "GET " + URL + " HTTP/1.1";
        assertThat(HttpRequestUtils.generateFilePath(line)).isEqualTo(HttpRequestUtils.ROOT_FILE_PATH + URL);
    }

    @Test
    void generateFilePath_root_true() {
        String URL = "/";
        String line = "GET " + URL + " HTTP/1.1";
        assertThat(HttpRequestUtils.generateFilePath(line)).isEqualTo(HttpRequestUtils.ROOT_FILE_PATH + INDEX_URL);
    }
}
