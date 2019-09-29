package http.support;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class HttpCookie {
    private static final String DELIMITER_OF_COOKIES = ";";
    private static final String DELIMITER_OF_COOKIE = "=";
    private static final String SPACE_DELIMITER = " ";

    private final Map<String, String> cookies = new HashMap<>();

    public static HttpCookie empty() {
        return new HttpCookie();
    }

    private HttpCookie() {
    }

    public HttpCookie(final String cookies) {
        if (!StringUtils.isBlank(cookies)) {
            Arrays.stream(cookies.split(DELIMITER_OF_COOKIES))
                    .map(line -> line.trim())
                    .map(line -> Arrays.asList(line.split(DELIMITER_OF_COOKIE)))
                    .forEach(line -> this.cookies.put(line.get(0), line.get(1)));
        }
    }

    public String getValue(String name) {
        return cookies.get(name);
    }

    public void addCookie(final String name, final String value) {
        cookies.put(name, value);
    }

    public String parse() {
        return String.join(SPACE_DELIMITER, parseCookies());
    }

    private List<String> parseCookies() {
        return this.cookies.entrySet().stream()
                .map(entry -> entry.getKey() + DELIMITER_OF_COOKIE + entry.getValue() + DELIMITER_OF_COOKIES)
                .collect(toList());
    }
}
