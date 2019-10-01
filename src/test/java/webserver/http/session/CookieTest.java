package webserver.http.session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.JSESSIONID_KEY;
import static utils.UtilData.JSESSIONID_VALUE;

class CookieTest {
    private static final String EQUAL = "=";

    private final Map<String, String> cookies = new HashMap<>();

    @BeforeEach
    void setUp() {
        cookies.put(JSESSIONID_KEY, JSESSIONID_VALUE);
    }

    @Test
    @DisplayName("CookieLine 파싱 테스트")
    void parse_cookie_line() {
        String cookieLine = JSESSIONID_KEY + EQUAL + JSESSIONID_VALUE;
        assertThat(new Cookie(cookieLine).getCookies(JSESSIONID_KEY)).isEqualTo(JSESSIONID_VALUE);
    }

    @AfterEach
    void tearDown() {
        cookies.clear();
    }
}