package http;

import java.util.Objects;

public class HttpCookie {
    private static final String DEFAULT_PATH = "/";

    private String key;
    private String value;
    private String path;

    public HttpCookie(String key, String value) {
        this.key = key;
        this.value = value;
        this.path = DEFAULT_PATH;
    }

    public boolean hasValueByKey(String key) {
        return this.key.equals(key);
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpCookie that = (HttpCookie) o;
        return key.equals(that.key) &&
                value.equals(that.value) &&
                path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, path);
    }
}
