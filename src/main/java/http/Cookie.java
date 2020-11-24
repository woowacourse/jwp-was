package http;

public class Cookie {
    private static final String SESSION_ID_KEY = "JSESSIONID";
    private static final String ROOT_PATH = "/";

    private String name;
    private String value;
    private String path;

    public static Cookie createSessionIdCookie(String value) {
        return new Cookie(SESSION_ID_KEY, value);
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value);
    }

    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.path = ROOT_PATH;
    }

    public boolean isSessionId() {
        return SESSION_ID_KEY.equals(name);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }
}
