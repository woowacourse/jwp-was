package kr.wootecat.dongle.application.http;

import java.util.Objects;

public class Cookie {

    public static final String PATH = "Path";

    private final String name;
    private final String value;
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

    public boolean hasValue(String name, String value) {
        return Objects.equals(name, this.name) &&
                Objects.equals(value, this.value);
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
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
}
