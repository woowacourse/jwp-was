package webserver.http.cookie;

public class Cookie {
    private final static String DEFAULT_PATH = "/";

    private final String key;
    private final String value;
    private final String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public Cookie(String key, String value) {
        this(key, value, DEFAULT_PATH);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Set-Cookie: " + key + "=" + value + "; " + "Path" + "=" + path;
    }
}
