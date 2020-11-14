package webserver.servlet.http;

public class Cookie {

    public static final String MAX_AGE = "Max-Age";
    public static final String PATH = "Path";

    private static final int NON_VALUE = -1;

    private final String name;
    private String value;
    private String path;
    private int maxAge = NON_VALUE;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public boolean hasPath() {
        return path != null;
    }

    public boolean hasMaxAge() {
        return maxAge != NON_VALUE;
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

    public void setPath(String path) {
        this.path = path;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
