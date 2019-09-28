package webserver.support;

import http.cookie.Cookie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CookieParserTest {

    @Test
    public void parse() {
        String cookies = "test1=test1; test2=test2";
        List<Cookie> expectedValue = Arrays.asList(
                Cookie.builder().name("test1").value("test1").build(),
                Cookie.builder().name("test2").value("test2").build()
        );
        assertThat(CookieParser.parse(cookies)).isEqualTo(expectedValue);
    }
}