package http.response;

public class Cookie {
    private String name;
    private String value;
    private String path;

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Set-Cookie: " + name + "=" + value + "; " + "Path=" + path + "\r\n";
    }
}
