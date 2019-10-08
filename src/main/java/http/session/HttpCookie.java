package http.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private static final Logger logger = LoggerFactory.getLogger(HttpCookie.class);

    private static final String COOKIE_DELIMITER = ";\\s*";
    private static final String NAME_AND_VALUE_DELIMITER = "=";
    private static final int COOKIE_NAME = 0;
    private static final int VALUE = 1;

    private Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie parse(String cookieLine) {
        HashMap<String, String> cookies = new HashMap<>();

        if (isEmptyCookieHeader(cookieLine)) {
            return new HttpCookie(new HashMap<>());
        }

        for (String cookie : cookieLine.split(COOKIE_DELIMITER)) {
            addCookie(cookies, cookie);
        }
        return new HttpCookie(cookies);
    }

    private static boolean isEmptyCookieHeader(String cookieLine) {
        return cookieLine == null || cookieLine.trim().isEmpty();
    }

    private static void addCookie(HashMap<String, String> cookies, String cookie) {
        String[] nameAndValue = cookie.split(NAME_AND_VALUE_DELIMITER);

        try {
            cookies.put(nameAndValue[COOKIE_NAME], nameAndValue[VALUE]);
        } catch (IndexOutOfBoundsException e) {
            LoggingUtils.logStackTrace(logger, e);
        }
    }

    public String getCookieValue(String cookieName) {
        return cookies.get(cookieName);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
