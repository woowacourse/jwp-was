package http.response;

public class Cookie {
    private String name;
    private String value;
    private String path;

    private Cookie(String name, String value, String path) {
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

    public static CookieBuilder builder() {
        return new CookieBuilder();
    }

    public static class CookieBuilder {
        private String name;
        private String value;
        private String path;

        private CookieBuilder() {}

        public CookieBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CookieBuilder value(String value) {
            this.value = value;
            return this;
        }

        public CookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public Cookie build() {
            return new Cookie(this.name, this.value, this.path);
        }
    }

    @Override
    public String toString() {
        return "Set-Cookie: " + name + "=" + value + "; " + "Path=" + path + "\r\n";
    }
}
