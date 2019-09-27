package webserver.support;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookieParserTest {

    @Test
    public void parse() {
        String cookies = "test1=test1; test2=test2";
        Map<String, String> expectedValue = Maps.newHashMap();
        expectedValue.put("test1", "test1");
        expectedValue.put("test2", "test2");
        assertThat(CookieParser.parse(cookies)).isEqualTo(expectedValue);
    }
}