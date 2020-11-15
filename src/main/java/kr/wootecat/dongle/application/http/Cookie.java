package kr.wootecat.dongle.application.http;

import java.util.Objects;

public class Cookie {

    public static final String PATH = "Path";

    private final String name;
    private String value;
    private String path;

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

    public boolean hasValue(String name, boolean value) {
        return Objects.equals(name, this.name) &&
                Objects.equals(String.valueOf(value), this.value);
    }
}
