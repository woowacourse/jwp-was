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

// Reference: https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Set-Cookie
// TODO: __Secure- 프리픽스 기능
// TODO: __Host- 프리픽스 기능
public class Cookie {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int ONE_YEAR = 60 * 60 * 24 * 365;
    private static final int DEFAULT_COOKIE_STRING_LENGTH = 256;
    private static final String TRUE = "true";
    private static final String EMPTY = "";
    private static final String ROOT = "/";
    private static final String PATH = "Path";
    private static final String UTF_8 = "UTF-8";
    private static final String DOMAIN = "Domain";
    private static final String SECURE = "Secure";
    private static final String EXPIRES = "Expires";
    private static final String SAME_SITE = "SameSite";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String MAX_AGE = "Max-Age";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final Escaper URL_ESCAPER = UrlEscapers.urlFragmentEscaper();
    private static final DateTimeFormatter HTML_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    // 각 필드들을 다른 자료구조 객체에 넣어보려 했지만, 오히려 복잡성이 증가하여 일단 이렇게 한다.
    private final Map<String, String> cookieJar;
    private final Set<String> booleanOptions;
    private ZonedDateTime expires = null;
    private Integer maxAgeSecond = null;
    private String domain = EMPTY;
    private String path = ROOT; // 기본값 주의
    private String sameSite = EMPTY;
    private boolean secure = false;
    private boolean httpOnly = false;

    public Cookie() {
        cookieJar = new HashMap<>();
        booleanOptions = new HashSet<>();
    }

    public Cookie(final String cookieString) {
        this();
        if (Objects.isNull(cookieString)) {
            return;
        }
        final String[] fragments = cookieString.split(COOKIE_DELIMITER);
        for (final String fragment : fragments) {
            processFragment(fragment);
        }
    }

    private void processFragment(final String fragment) {
        final String[] maybeTuple = fragment.split(KEY_VALUE_DELIMITER, 2);
        if (maybeTuple.length == 2) {
            processTuple(maybeTuple);
            return;
        }
        final String option = maybeTuple[KEY_INDEX];
        switch (option) {
            case SECURE:
                setSecure(true);
                break;
            case HTTP_ONLY:
                setHttpOnly(true);
                break;
            default:
                booleanOptions.add(option);
                break;
        }
    }

    private void processTuple(final String[] tuple) {
        final String key = tuple[KEY_INDEX];
        final String value = tuple[VALUE_INDEX];
        switch (key) {
            case EXPIRES:
                setExpires(parseExpires(value));
                break;
            case MAX_AGE:
                setMaxAge(Integer.parseInt(value));
                break;
            case DOMAIN:
                setDomain(value);
                break;
            case PATH:
                setPath(value);
                break;
            case SAME_SITE:
                setSameSite(value);
                break;
            default:
                set(urlDecode(key), urlDecode(value));
                break;
        }
    }

    public String get(final String keyOrOption) {
        if (booleanOptions.contains(keyOrOption)) {
            return TRUE;
        }
        return cookieJar.getOrDefault(keyOrOption, EMPTY);
    }

    public void set(final String option) {
        booleanOptions.add(option);
    }

    public void set(final String key, final String value) {
        cookieJar.put(key, value);
    }

    public String remove(final String keyOrOption) {
        if (booleanOptions.contains(keyOrOption)) {
            booleanOptions.remove(keyOrOption);
            return TRUE;
        }
        return cookieJar.remove(keyOrOption);
    }

    public void clear() {
        cookieJar.clear();
        booleanOptions.clear();
    }

    public boolean isEmpty() {
        return cookieJar.isEmpty() && booleanOptions.isEmpty();
    }

    public boolean containsKey(final String keyOrOption) {
        return cookieJar.containsKey(keyOrOption) || booleanOptions.contains(keyOrOption);
    }

    public int size() {
        return cookieJar.size() + booleanOptions.size();
    }

    public Map<String, String> entries() {
        final Map<String, String> result = new HashMap<>(cookieJar);
        for (final String item : booleanOptions) {
            result.put(item, TRUE);
        }
        return Collections.unmodifiableMap(result);
    }

    @Nullable
    public ZonedDateTime getExpires() {
        return expires;
    }

    public void setExpires(final ZonedDateTime expires) {
        this.expires = expires;
    }

    public void setExpires(final String expires) {
        this.setExpires(parseExpires(expires));
    }

    @Nullable
    public Integer getMaxAge() {
        return maxAgeSecond;
    }

    public void setMaxAge(final int maxAgeSecond) {
        if (ONE_YEAR < maxAgeSecond) {
            this.maxAgeSecond = ONE_YEAR; // RFC 2616 Section 14.21에 따른 값
        }
        this.maxAgeSecond = maxAgeSecond;
    }

    public void setMaxAge(final String maxAgeSecond) {
        this.setMaxAge(Integer.parseInt(maxAgeSecond));
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getSameSite() {
        return sameSite;
    }

    public void setSameSite(final String sameSite) {
        this.sameSite = sameSite;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(final boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(final boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    private ZonedDateTime parseExpires(final String text) throws DateTimeParseException {
        return ZonedDateTime.parse(text, HTML_TIME_FORMATTER);
    }

    private String expiresToString(final ZonedDateTime expires) {
        return Objects.isNull(expires) ? EMPTY : expires.format(HTML_TIME_FORMATTER);
    }

    private String maxAgeToString(final Integer maxAgeSecond) {
        return Objects.isNull(maxAgeSecond) ? EMPTY : maxAgeSecond.toString();
    }

    // StringJoiner 사용이 되려 불편하다 - 대신 StringBuilder 사용
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(DEFAULT_COOKIE_STRING_LENGTH);
        cookieJarToString(builder);
        optionsToString(builder);
        removeLastDelimiter(builder);
        return builder.toString();
    }

    private void optionsToString(final StringBuilder builder) {
        toStringHelper(builder, EXPIRES, expiresToString(expires));
        toStringHelper(builder, MAX_AGE, maxAgeToString(maxAgeSecond));
        toStringHelper(builder, DOMAIN, domain);
        toStringHelper(builder, PATH, path);
        toStringHelper(builder, SECURE, secure);
        toStringHelper(builder, HTTP_ONLY, httpOnly);
        toStringHelper(builder, SAME_SITE, sameSite);
        itemsToString(builder, booleanOptions);
    }

    private void cookieJarToString(final StringBuilder builder) {
        for (final String key : cookieJar.keySet()) {
            builder
                    .append(URL_ESCAPER.escape(key))
                    .append(KEY_VALUE_DELIMITER)
                    .append(URL_ESCAPER.escape(cookieJar.get(key)))
                    .append(COOKIE_DELIMITER);
        }
    }

    private void toStringHelper(final StringBuilder builder, final String key, final String value) {
        if (EMPTY.equals(value)) {
            return;
        }
        builder
                .append(key)
                .append(KEY_VALUE_DELIMITER)
                .append(value)
                .append(COOKIE_DELIMITER);
    }

    private void toStringHelper(final StringBuilder builder, final String option, final boolean value) {
        if (value) {
            builder.append(option).append(COOKIE_DELIMITER);
        }
    }

    private void itemsToString(final StringBuilder builder, final Iterable<String> items) {
        for (final String item : items) {
            if (!EMPTY.equals(item)) {
                toStringHelper(builder, item, true);
            }
        }
    }

    private void removeLastDelimiter(final StringBuilder builder) {
        if (builder.length() > 0) {
            builder.setLength(builder.length() - COOKIE_DELIMITER.length());
        }
    }

    private String urlDecode(final String encodedString) {
        try {
            return URLDecoder.decode(encodedString, UTF_8);
        } catch (final UnsupportedEncodingException ignored) {
            return EMPTY;
        }
    }
}
