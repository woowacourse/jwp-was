package webserver.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private static final Logger log = LoggerFactory.getLogger(Cookie.class);
    private final Map<String, String> cookies;

    public Cookie(String cookieLine) {
        this.cookies = parseCookies(cookieLine);
        cookies.forEach((key, value) -> log.debug("Cookie value : {}, {}", key, value));
    }

    private Map<String, String> parseCookies(String cookieLine) {
        if(StringUtils.isEmpty(cookieLine)) {
            return new HashMap<>();
        }
        return extractCookies(cookieLine.split("; "));
    }

    private Map<String, String> extractCookies(String[] attribute) {
        return Arrays.stream(attribute)
                .map(cookie -> cookie.split("="))
                .collect(Collectors.toMap(index -> index[0],
                        index -> index.length > 1 ? index[1]: ""));
    }

    public String getCookies(String key) {
        return cookies.get(key);
    }
}