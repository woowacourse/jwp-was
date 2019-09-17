package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestUtilsTest {

    @Test
    void parseURLPath_indexUrl_true() {
        String URL = "/index.html";
        String input = "GET " + URL + " HTTP/1.1";
        assertThat(HttpRequestUtils.parseURLPath(input)).isEqualTo(URL);
    }

    @Test
    void parseURLPath_emptyUrl_error() {
        String input = "GET HTTP/1.1";
        assertThrows(IllegalArgumentException.class,()->{
           HttpRequestUtils.parseURLPath(input);
        });
    }
}
