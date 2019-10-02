package http.cookie;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class CookieParserTest {
    @Test
    void parseTest() {
        List<String> input = Arrays.asList("a=1", "b=", "c", BLANK, null);
        Cookies cookies = CookieParser.parse(input);

        assertEquals(cookies.getCookie("a").getValue(), "1");
        assertEquals(cookies.getCookie("b").getValue(), BLANK);
    }
}