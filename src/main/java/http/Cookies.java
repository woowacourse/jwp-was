package http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cookies implements Cloneable {

    private static final String SESSION_ID = "SessionId";
    private static final String EQUAL = "=";
    private static final String SEMICOLON = ";";
    private static final String BLANK = " ";
    private static final String EMPTY = "";
    private static final int MINIMUM_LENGTH_TWO = 2;

    private final Map<String, String> cookies;

    private Cookies(final Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookies from(final String cookies) {
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return new Cookies(new LinkedHashMap<>());
        }
        Map<String, String> savedCookies = new LinkedHashMap<>();
        String[] flatCookie = cookies.split(SEMICOLON);
        for (String cookie : flatCookie) {
            String[] data = cookie.split(EQUAL);
            inputCookieData(savedCookies, data);
        }
        return new Cookies(savedCookies);
    }

    private static void inputCookieData(final Map<String, String> savedCookies, final String[] data) {
        if (data.length == MINIMUM_LENGTH_TWO) {
            savedCookies.put(data[0].trim(), data[1].trim());
        }
        savedCookies.put(data[0].trim(), EMPTY);
    }

    public void addCookie(final String name, final String value) {
        this.cookies.put(name, value);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String flat() {
        return this.cookies.entrySet().stream()
                .map(key -> key.getKey() + EQUAL + key.getValue())
                .collect(Collectors.joining(SEMICOLON + BLANK));
    }

    public String getSessionId() {
        return cookies.get(SESSION_ID);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
