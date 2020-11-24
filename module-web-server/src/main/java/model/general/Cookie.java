package model.general;

public class Cookie {

    private static final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final String key;
    private final String value;

    private Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Cookie of(String rawCookie) {
        String[] separatedCookie = rawCookie.split(COOKIE_KEY_VALUE_SEPARATOR);
        String key = separatedCookie[KEY_INDEX].trim();
        String value = separatedCookie[VALUE_INDEX].trim();

        return new Cookie(key, value);
    }

    public boolean isSameKey(String key) {
        return this.key.equals(key);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
