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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Reference: https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Set-Cookie
// TODO: __Secure- 프리픽스 기능
// TODO: __Host- 프리픽스 기능
public class Cookie {
    private static final int ONE_YEAR = 60 * 60 * 24 * 365;
    private static final int DEFAULT_COOKIE_STRING_LENGTH = 256;
    private static final String EMPTY = "";
    private static final String UTF_8 = "UTF-8";
    private static final String EXPIRES = "Expires";
    private static final String MAX_AGE = "Max-Age";
    private static final String SECURE = "Secure";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EXPIRES_DELIMITER = EXPIRES + KEY_VALUE_DELIMITER;
    private static final String MAX_AGE_DELIMITER = MAX_AGE + KEY_VALUE_DELIMITER;
    private static final String SECURE_DELIMITER = SECURE + COOKIE_DELIMITER;
    private static final String HTTP_ONLY_DELIMITER = HTTP_ONLY + COOKIE_DELIMITER;
    private static final Escaper URL_ESCAPER = UrlEscapers.urlFragmentEscaper();
    private static final DateTimeFormatter HTML_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> cookieJar;
    private ZonedDateTime expires = null;
    private Integer maxAgeSecond = null;
    private boolean isSecure = false;
    private boolean isHttpOnly = false;

    public Cookie() {
        this.cookieJar = new HashMap<>();
    }

    public Cookie(final String cookieString) {
        this();
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
        switch (maybeTuple[KEY_INDEX]) {
            case SECURE:
                setSecure(true);
                break;
            case HTTP_ONLY:
                setHttpOnly(true);
                break;
            default:
                break;
        }
    }

    private void processTuple(final String[] tuple) {
        switch (tuple[KEY_INDEX]) {
            case EXPIRES:
                setExpires(parseExpires(tuple[1]));
                break;
            case MAX_AGE:
                setMaxAge(Integer.parseInt(tuple[1]));
                break;
            default:
                this.set(urlDecode(tuple[KEY_INDEX]), urlDecode(tuple[VALUE_INDEX]));
                break;
        }
    }

    public String get(final String key) {
        return this.cookieJar.getOrDefault(key, EMPTY);
    }

    public void set(final String key, final String value) {
        this.cookieJar.put(key, value);
    }

    public String remove(final String key) {
        return this.cookieJar.remove(key);
    }

    public void clear() {
        this.cookieJar.clear();
    }

    public boolean isEmpty() {
        return this.cookieJar.isEmpty();
    }

    public boolean containsKey(final String key) {
        return this.cookieJar.containsKey(key);
    }

    public int size() {
        return this.cookieJar.size();
    }

    public Map<String, String> entries() {
        return Collections.unmodifiableMap(this.cookieJar);
    }

    @Nullable
    public ZonedDateTime getExpires() {
        return expires;
    }

    public void setExpires(final ZonedDateTime expires) {
        this.expires = expires;
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

    public boolean isSecure() {
        return isSecure;
    }

    public void setSecure(final boolean secure) {
        isSecure = secure;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }

    public void setHttpOnly(final boolean httpOnly) {
        isHttpOnly = httpOnly;
    }

    private ZonedDateTime parseExpires(final String text) throws DateTimeParseException {
        return ZonedDateTime.parse(text, HTML_TIME_FORMATTER);
    }

    // TODO: 반복 코드의 중복 제거 리팩토링 방안 찾아보기
    private String expiresToString(final ZonedDateTime expires) {
        return Objects.isNull(expires) ? EMPTY
                : EXPIRES_DELIMITER + expires.format(HTML_TIME_FORMATTER) + COOKIE_DELIMITER;
    }

    private String maxAgeToString(final Integer maxAgeSecond) {
        return Objects.isNull(maxAgeSecond) ? EMPTY
                : MAX_AGE_DELIMITER + maxAgeSecond.toString() + COOKIE_DELIMITER;
    }

    private String secureToString() {
        return isSecure ? SECURE_DELIMITER : EMPTY;
    }

    private String httpOnlyToString() {
        return isHttpOnly ? HTTP_ONLY_DELIMITER : EMPTY;
    }

    // StringJoiner 사용 실패 - 대신 StringBuilder 사용
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(DEFAULT_COOKIE_STRING_LENGTH);

        cookieJarToString(builder);
        builder.append(expiresToString(this.expires));
        builder.append(maxAgeToString(this.maxAgeSecond));
        builder.append(secureToString());
        builder.append(httpOnlyToString());

        if (builder.length() > 0) {
            builder.setLength(builder.length() - COOKIE_DELIMITER.length()); // 마지막 부분의 delimiter 빼주기
        }
        return builder.toString();
    }

    private void cookieJarToString(final StringBuilder builder) {
        for (final String key : cookieJar.keySet()) {
            builder
                    .append(URL_ESCAPER.escape(key))
                    .append(KEY_VALUE_DELIMITER)
                    .append("\"")
                    .append(URL_ESCAPER.escape(cookieJar.get(key)))
                    .append("\"")
                    .append(COOKIE_DELIMITER);
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
