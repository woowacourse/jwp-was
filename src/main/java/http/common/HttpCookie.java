package http.common;

import java.time.LocalDateTime;

public class HttpCookie {
    private String name;
    private String value;
    private LocalDateTime expires;
    private int maxAge;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;

    public HttpCookie(final String name, final String value, final LocalDateTime expires, final int maxAge,
                      final String domain, final String path, final boolean secure, final boolean httpOnly) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.maxAge = maxAge;
        this.domain = domain;
        this.path = path;
        this.secure = secure;
        this.httpOnly = httpOnly;
    }

    public static HttpCookieBuilder builder(final String name, final String value) {
        return new HttpCookieBuilder(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    @Override
    public String toString() {
        return "HttpCookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", expires=" + expires +
                ", maxAge=" + maxAge +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                '}';
    }

    public static class HttpCookieBuilder {
        private final String name;
        private final String value;
        private LocalDateTime expires;
        private int maxAge = -1;
        private String domain;
        private String path;
        private boolean secure;
        private boolean httpOnly;

        private HttpCookieBuilder(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public HttpCookieBuilder expires(final String expires) {
            this.expires = LocalDateTime.parse(expires);
            return this;
        }

        public HttpCookieBuilder expires(final LocalDateTime expires) {
            this.expires = expires;
            return this;
        }
        public HttpCookieBuilder maxAge(final int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public HttpCookieBuilder domain(final String domain) {
            this.domain = domain;
            return this;
        }

        public HttpCookieBuilder path(final String path) {
            this.path = path;
            return this;
        }

        public HttpCookieBuilder secure(final boolean secure) {
            this.secure = secure;
            return this;
        }

        public HttpCookieBuilder httpOnly(final boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public HttpCookie build() {
            return new HttpCookie(name, value, expires, maxAge, domain, path, secure, httpOnly);
        }
    }
}
