package webserver.domain;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import javax.annotation.Nullable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cookie implements Cloneable {
    private static final int ONE_YEAR = 60 * 60 * 24 * 365;
    private static final String EMPTY = "";
    private static final String ROOT = "/";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String PATH = "Path";
    private static final String DOMAIN = "Domain";
    private static final String SECURE = "Secure";
    private static final String EXPIRES = "Expires";
    private static final String SAME_SITE = "SameSite";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String MAX_AGE = "Max-Age";
    private static final Escaper URL_ESCAPER = UrlEscapers.urlFragmentEscaper();
    private static final DateTimeFormatter HTML_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    private String name;
    private String value;
    private ZonedDateTime expires;
    private Integer maxAgeSecond;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;
    private String sameSite;

    Cookie() {
        fieldsReset();
    }

    public Cookie(final String name, final String value) {
        this();
        setName(name);
        setValue(value);
    }

    private void fieldsReset() {
        name = EMPTY;
        value = EMPTY;
        expires = null;
        maxAgeSecond = null;
        domain = EMPTY;
        path = ROOT;
        secure = false;
        httpOnly = false;
        sameSite = EMPTY;
    }

    public String getName() {
        return name;
    }

    public Cookie setName(final String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Cookie setValue(final String value) {
        this.value = value;
        return this;
    }

    @Nullable
    public ZonedDateTime getExpires() {
        return expires;
    }

    public Cookie setExpires(final ZonedDateTime expires) {
        this.expires = expires;
        return this;
    }

    @Nullable
    public Integer getMaxAge() {
        return maxAgeSecond;
    }

    public Cookie setMaxAge(int maxAgeSecond) {
        if (ONE_YEAR < maxAgeSecond) {
            maxAgeSecond = ONE_YEAR;
        }
        this.maxAgeSecond = maxAgeSecond;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Cookie setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Cookie setPath(final String path) {
        this.path = path;
        return this;
    }

    public boolean isSecure() {
        return secure;
    }

    public Cookie setSecure(final boolean secure) {
        this.secure = secure;
        return this;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public Cookie setHttpOnly(final boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public String getSameSite() {
        return sameSite;
    }

    public Cookie setSameSite(final String sameSite) {
        this.sameSite = sameSite;
        return this;
    }

    public boolean isEmpty() {
        return name.isEmpty() || value.isEmpty();
    }

    private String expiresToString() {
        return Objects.isNull(expires) ? EMPTY : expires.format(HTML_TIME_FORMATTER);
    }

    private String maxAgeToString() {
        return Objects.isNull(maxAgeSecond) ? EMPTY : maxAgeSecond.toString();
    }

    private void keyValueToString(final StringBuilder builder, final String key, final String value) {
        if (Objects.isNull(value) || EMPTY.equals(value)) {
            return;
        }
        builder
                .append(key)
                .append(KEY_VALUE_DELIMITER)
                .append(value)
                .append(COOKIE_DELIMITER);
    }

    private void optionToString(final StringBuilder builder, final String optionName, final boolean option) {
        if (!option) {
            return;
        }
        builder.append(optionName).append(COOKIE_DELIMITER);
    }

    private void removeLastDelimiter(final StringBuilder builder) {
        if (0 < builder.length()) {
            builder.setLength(builder.length() - COOKIE_DELIMITER.length());
        }
    }

    private void makeString(final StringBuilder builder) {
        keyValueToString(builder, URL_ESCAPER.escape(name), URL_ESCAPER.escape(value));
        keyValueToString(builder, EXPIRES, expiresToString());
        keyValueToString(builder, MAX_AGE, maxAgeToString());
        keyValueToString(builder, DOMAIN, domain);
        keyValueToString(builder, PATH, path);
        optionToString(builder, SECURE, secure);
        optionToString(builder, HTTP_ONLY, httpOnly);
        keyValueToString(builder, SAME_SITE, sameSite);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return EMPTY;
        }
        final StringBuilder builder = new StringBuilder();
        makeString(builder);
        removeLastDelimiter(builder);
        return builder.toString();
    }

    @Override
    public boolean equals(final Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        final Cookie cookie = (Cookie) another;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value) &&
                Objects.equals(domain, cookie.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, domain);
    }

    @Override
    protected Cookie clone() throws CloneNotSupportedException {
        return (Cookie) super.clone();
    }
}
