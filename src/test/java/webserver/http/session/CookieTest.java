package webserver.http.session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

class CookieTest {
    private static final String EQUAL = "=";

    private final Map<String, String> cookies = new HashMap<>();

    @BeforeEach
    void setUp() {
        cookies.put(JSESSIONED_ID, JSESSIONED_VALUE);
    }

    @Test
    @DisplayName("CookieLine 파싱 테스트")
    void parse_cookie_line() {
        String cookieLine = JSESSIONED_ID + EQUAL + JSESSIONED_VALUE;
        assertThat(new Cookie(cookieLine).getCookies(JSESSIONED_ID)).isEqualTo(JSESSIONED_VALUE);
    }

    @AfterEach
    void tearDown() {
        cookies.clear();
    }
}