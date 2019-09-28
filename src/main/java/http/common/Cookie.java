package http.common;

public class Cookie {
    private static final String MAX_AGE = "Max-Age";
    private static final String PATH = "Path";
    private static final String EQUAL = "=";
    private static final String SEMICOLON = ";";

    private String name;
    private String value;
    private int maxAge = -1;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(EQUAL).append(value);
        stringBuilder.append(SEMICOLON).append(" ").append(MAX_AGE).append(EQUAL).append(maxAge);
        if (path != null) {
            stringBuilder.append(SEMICOLON).append(" ").append(PATH).append(EQUAL).append(path);
        }
        return stringBuilder.toString();
    }
}
