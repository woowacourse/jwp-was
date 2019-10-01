package http.cookie;

import utils.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cookie {
    public static final Cookie EMPTY = new Cookie.Builder(null, null).build();
    public static final String MAX_AGE = "Max-Age";
    public static final String PATH = "Path";
    public static final String DOMAIN = "Domain";
    public static final String SECURE = "Secure";
    public static final String KEY_VALUE_FORMAT = "%s=%s;";

    private final String name;
    private final String value;
    private final String maxAge;
    private final String path;
    private final String domain;
    private final boolean secure;

    public static class Builder {
        private final String name;
        private final String value;
        private String maxAge;
        private String path;
        private String domain;
        private boolean secure;

        public Builder(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Builder maxAge(LocalDateTime maxAge) {
            if (maxAge != null) {
                this.maxAge = maxAge.toString();
            }
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder secure(boolean secure) {
            this.secure = secure;
            return this;
        }

        public Cookie build() {
            return new Cookie(this);
        }
    }

    private Cookie(Builder builder) {
        this.name = builder.name;
        this.value = builder.value;
        this.maxAge = builder.maxAge;
        this.path = builder.path;
        this.domain = builder.domain;
        this.secure = builder.secure;
    }

    public static boolean isNotEmpty(Cookie cookie) {
        return !cookie.equals(EMPTY);
    }

    public boolean matchName(String name) {
        return this.name == name || this.name.equals(name);
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        writeCookieField(stringBuilder, name, value);
        writeCookieField(stringBuilder, MAX_AGE, maxAge);
        writeCookieField(stringBuilder, PATH, path);
        writeCookieField(stringBuilder, DOMAIN, domain);

        if (secure) {
            stringBuilder.append(String.format("%s;", SECURE));
        }

        return stringBuilder.toString();
    }

    private void writeCookieField(StringBuilder stringBuilder, String name, String value) {
        if (!StringUtils.isNull(value)) {
            stringBuilder.append(String.format(KEY_VALUE_FORMAT, name, value));
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return name.equals(cookie.name) &&
                value.equals(cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}