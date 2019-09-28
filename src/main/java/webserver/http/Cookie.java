package webserver.http;

import webserver.http.utils.StringUtils;

import java.util.Objects;

import static webserver.http.Cookies.DELIMITER;
import static webserver.http.Cookies.DELIMITER_PAIR;

public class Cookie {
    private static final String PATH_KEY_NAME = "Path";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String SECURE = "Secure";
    private static final String MAX_AGE = "max-age";

    private final String name;
    private String value;
    private int maxAge = -1;
    private String path;
    private boolean secure;
    private boolean httpOnly;

    public Cookie(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public boolean matchName(final String name) {
        return this.name.equals(name);
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setMaxAge(final int maxAge) {
        this.maxAge = maxAge;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setSecure(final boolean secure) {
        this.secure = secure;
    }

    public void setHttpOnly(final boolean httpOnly) {
        this.httpOnly = httpOnly;
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

    public int getMaxAge() {
        return maxAge;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public boolean hasMaxAge() {
        return maxAge != -1;
    }

    public boolean hasPath() {
        return StringUtils.isNotEmpty(path);
    }

    public String parseInfoAsString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s%s%s", name, DELIMITER_PAIR, value, DELIMITER))
                .append(hasMaxAge() ? String.format("%s=%s%s", MAX_AGE, maxAge, DELIMITER) : "")
                .append(hasPath() ? String.format("%s=%s%s", PATH_KEY_NAME, path, DELIMITER) : "")
                .append(secure ? SECURE + DELIMITER : "")
                .append(httpOnly ? HTTP_ONLY + DELIMITER : "");

        sb.delete(sb.length() - DELIMITER.length(), sb.length());
        return sb.toString();
    }

    public static CookieBuilder builder(final String name, final String value) {
        return new CookieBuilder(name, value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return parseInfoAsString();
    }

    public static final class CookieBuilder {
        private String name;
        private String value;
        private int maxAge;
        private String path;
        private boolean secure;
        private boolean httpOnly;

        private CookieBuilder(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public CookieBuilder maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public CookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public CookieBuilder secure(boolean secure) {
            this.secure = secure;
            return this;
        }

        public CookieBuilder httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public Cookie build() {
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(maxAge);
            cookie.setPath(path);
            cookie.setSecure(secure);
            cookie.setHttpOnly(httpOnly);
            return cookie;
        }
    }
}
