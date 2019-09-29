package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    public static final String DELIMITER_OF_COOKIES = ";";
    public static final String DELIMITER_OF_COOKIE = "=";

    private final Map<String, String> cookies = new HashMap<>();

    public HttpCookie() {
    }

    public HttpCookie(final String cookies) {
        if (!StringUtils.isBlank(cookies)) {
            Arrays.stream(cookies.split(DELIMITER_OF_COOKIES))
                    .map(line -> line.trim())
                    .map(line -> Arrays.asList(line.split(DELIMITER_OF_COOKIE)))
                    .forEach(line -> this.cookies.put(line.get(0), line.get(1)));
        }
    }

    public String getValue(String key) {
        return cookies.get(key);
    }
}
