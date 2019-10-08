package webserver.httpelement;

import utils.StringUtils;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class HttpSetCookie implements HttpHeaderField {
    private enum SameSite {
        STRICT,
        LAX;

        @Override
        public String toString() {
            return StringUtils.screamingSnakeCaseToStartCase(this.name());
        }
    }

    private final String key;
    private final String value;
    private final LocalDateTime expires;
    private final Integer maxAge;
    private final String domain;
    private final String path;
    private final boolean secure;
    private final boolean httpOnly;
    private final SameSite sameSite;

    public static class HttpSetCookieBuilder {
        private final String key;
        private final String value;
        private LocalDateTime expires;
        private Integer maxAge;
        private String domain;
        private String path;
        private boolean secure = false;
        private boolean httpOnly = false;
        private SameSite sameSite;

        public HttpSetCookieBuilder(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public HttpSetCookie.HttpSetCookieBuilder expires(LocalDateTime expires) {
            this.expires = expires;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder secure(boolean isSecure) {
            this.secure = isSecure;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder httpOnly(boolean isHttpOnly) {
            this.httpOnly = isHttpOnly;
            return this;
        }

        public HttpSetCookie.HttpSetCookieBuilder sameSite(SameSite sameSite) {
            this.sameSite = sameSite;
            return this;
        }

        public HttpSetCookie build() {
            return new HttpSetCookie(this);
        }
    }

    public static HttpSetCookie.HttpSetCookieBuilder builder(String key, String value) {
        return new HttpSetCookie.HttpSetCookieBuilder(key, value);
    }

    private HttpSetCookie(HttpSetCookie.HttpSetCookieBuilder builder) {
        this.key = builder.key;
        this.value = builder.value;
        this.expires = builder.expires;
        this.maxAge = builder.maxAge;
        this.domain = builder.domain;
        this.path = builder.path;
        this.secure = builder.secure;
        this.httpOnly = builder.httpOnly;
        this.sameSite = builder.sameSite;
    }

    public HttpSetCookie(String key, String value) {
        this(new HttpSetCookie.HttpSetCookieBuilder(key, value));
    }

    @Override
    public String toString() {
        final StringBuilder acc = new StringBuilder(String.format("%s=%s", this.key, this.value));
        if (this.expires != null) {
            addAttributesToString(acc, "Expires", toHttpDate(this.expires));
        }
        if (this.maxAge != null) {
            addAttributesToString(acc, "Max-Age", String.valueOf(this.maxAge));
        }
        if (this.domain != null) {
            addAttributesToString(acc, "Domain", this.domain);
        }
        if (this.path != null) {
            addAttributesToString(acc, "Path", this.path);
        }
        if (this.secure) {
            acc.append("; Secure");
        }
        if (this.httpOnly) {
            acc.append("; HttpOnly");
        }
        if (this.sameSite != null) {
            addAttributesToString(acc, "SameSite", this.sameSite.toString());
        }
        return acc.toString();
    }

    private void addAttributesToString(StringBuilder acc, String key, String value) {
        acc.append(String.format("; %s=%s", key, value));
    }

    private String toHttpDate(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O").format(dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpSetCookie)) {
            return false;
        }
        final HttpSetCookie rhs = (HttpSetCookie) o;
        return this.secure == rhs.secure &&
                this.httpOnly == rhs.httpOnly &&
                Objects.equals(this.key, rhs.key) &&
                Objects.equals(this.value, rhs.value) &&
                Objects.equals(this.expires, rhs.expires) &&
                Objects.equals(this.maxAge, rhs.maxAge) &&
                Objects.equals(this.domain, rhs.domain) &&
                Objects.equals(this.path, rhs.path) &&
                this.sameSite == rhs.sameSite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.key,
                this.value,
                this.expires,
                this.maxAge,
                this.domain,
                this.path,
                this.secure,
                this.httpOnly,
                this.sameSite
        );
    }
}