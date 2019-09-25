package http.common;

public class Cookie {
    private static final String MAX_AGE = "Max-Age";
    private static final String PATH = "Path";
    private static final String EQUAL = "=";
    private static final String SEMI_COLON = ";";

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

    public void setValue(String value) {
        this.value = value;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(EQUAL).append(value);
        stringBuilder.append(SEMI_COLON).append(" ").append(MAX_AGE).append(EQUAL).append(maxAge);
        if (path != null) {
            stringBuilder.append(SEMI_COLON).append(" ").append(PATH).append(EQUAL).append(path);
        }
        return stringBuilder.toString();
    }
}
