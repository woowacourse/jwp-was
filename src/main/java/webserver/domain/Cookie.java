package webserver.domain;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BiFunction;

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
    private static final Map<String, BiFunction<String, Cookie, Cookie>> OPTION_MAPPER = new HashMap<>();

    static {
        OPTION_MAPPER.put(EXPIRES.toLowerCase(), (value, cookie) -> cookie.setExpires(parseExpires(value)));
        OPTION_MAPPER.put(MAX_AGE.toLowerCase(), (value, cookie) -> cookie.setMaxAge(Integer.parseInt(value)));
        OPTION_MAPPER.put(DOMAIN.toLowerCase(), (value, cookie) -> cookie.setDomain(value));
        OPTION_MAPPER.put(PATH.toLowerCase(), (value, cookie) -> cookie.setPath(value));
        OPTION_MAPPER.put(SECURE.toLowerCase(), (value, cookie) -> cookie.setSecure(true));
        OPTION_MAPPER.put(HTTP_ONLY.toLowerCase(), (value, cookie) -> cookie.setHttpOnly(true));
        OPTION_MAPPER.put(SAME_SITE.toLowerCase(), (value, cookie) -> cookie.setSameSite(value));
    }

    private String name;
    private String value;
    private ZonedDateTime expires;
    private Integer maxAgeSecond;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;
    private String sameSite;

    private Cookie() {
        fieldsReset();
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

    public List<Cookie> fromString(final String rawCookies) {
        return new Maker(rawCookies).makeList();
    }

    @Override
    protected Cookie clone() throws CloneNotSupportedException {
        return (Cookie) super.clone();
    }

    private static ZonedDateTime parseExpires(final String text) throws DateTimeParseException {
        return ZonedDateTime.parse(text, HTML_TIME_FORMATTER);
    }

    private class Maker {
        private static final int KEY_INDEX = 0;
        private static final int VALUE_INDEX = 1;
        private static final int LIMIT = 2;
        private static final String UTF_8 = "UTF-8";
        private final Map<String, String> cookieJar = new HashMap<>();
        private final Cookie optionCookie = new Cookie();

        Maker(final String rawCookies) {
            if (Objects.isNull(rawCookies)) {
                return;
            }
            final String[] fragments = rawCookies.split(";");
            for (final String fragment : fragments) {
                processFragment(fragment.trim());
            }
        }

        private void processFragment(final String fragment) {
            final String[] maybeTuple = fragment.split(KEY_VALUE_DELIMITER, LIMIT);
            if (maybeTuple.length == LIMIT) {
                processTuple(maybeTuple);
                return;
            }
            processBooleanOption(maybeTuple[KEY_INDEX]);
        }

        private void processTuple(final String[] tuple) {
            final String key = tuple[KEY_INDEX];
            final String value = tuple[VALUE_INDEX];
            final String keyLowerCase = key.toLowerCase();
            if (OPTION_MAPPER.containsKey(keyLowerCase)) {
                OPTION_MAPPER.get(keyLowerCase).apply(value, optionCookie);
                return;
            }
            cookieJar.put(urlDecode(key), urlDecode(value));
        }

        private void processBooleanOption(final String option) {
            OPTION_MAPPER.getOrDefault(option.toLowerCase(), (value, cookie) -> cookie).apply(null, optionCookie);
        }

        private String urlDecode(final String encodedString) {
            try {
                return URLDecoder.decode(encodedString, UTF_8);
            } catch (final UnsupportedEncodingException ignored) {
                return EMPTY;
            }
        }

        List<Cookie> makeList() {
            final List<Cookie> cookies = new ArrayList<>();
            try {
                for (final String key : cookieJar.keySet()) {
                    final Cookie item = optionCookie.clone();
                    item.setName(key);
                    item.setValue(cookieJar.get(key));
                    cookies.add(item);
                }
            } catch (final CloneNotSupportedException ignored) {}
            return cookies;
        }
    }
}
