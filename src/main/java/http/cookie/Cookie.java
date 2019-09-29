package http.cookie;

import java.util.Objects;

public class Cookie {
    private String name;
    private String value;
    private String path;

    private Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public static CookieBuilder builder() {
        return new CookieBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value) &&
                Objects.equals(path, cookie.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, path);
    }

    @Override
    public String toString() {
        return "Set-Cookie: " + name + "=" + value + "; " + "Path=" + path + "\r\n";
    }

    public static class CookieBuilder {
        private String name;
        private String value;
        private String path;

        private CookieBuilder() {
        }

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
}
