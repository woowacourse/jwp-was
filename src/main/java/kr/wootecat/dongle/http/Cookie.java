package kr.wootecat.dongle.http;

import java.util.Objects;

public class Cookie {

    public static final String PATH = "Path";

    private final String name;
    private final String value;
    private final String path;

    public Cookie(String name, String value) {
        this(name, value, null);
    }

    public Cookie(String name, boolean value) {
        this(name, String.valueOf(value), null);
    }

    public Cookie(String name, boolean value, String path) {
        this(name, String.valueOf(value), path);
    }

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
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
}
