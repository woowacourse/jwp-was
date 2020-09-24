package webserver;

public enum EntityHeader {
    CONTENT_LENGTH("Content-Length: %s"),
    CONTENT_TYPE("Content-Type: %s; charset=utf-8");

    private final String name;

    EntityHeader(String name) {
        this.name = name;
    }

    public boolean match(String line) {
        return line.contains(this.name);
    }

    public String get() {
        return name;
    }

    public String make(String value) {
        return String.format(name, value);
    }
}
